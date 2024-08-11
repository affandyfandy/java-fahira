package com.midterm.group4.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.time.LocalDateTime;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Propagation;

import com.midterm.group4.data.model.OrderItem;
import com.midterm.group4.data.model.Product;
import com.midterm.group4.data.repository.OrderItemRepository;
import com.midterm.group4.data.repository.ProductRepository;

@DataJpaTest
@Transactional(propagation = Propagation.NOT_SUPPORTED)
public class OrderItemRepositoryTest {
    
    @Autowired
    private OrderItemRepository repository;

    @Autowired
    private ProductRepository productRepository;

    private Product product1;
    private Product product2;
    private OrderItem orderItem1;
    private OrderItem orderItem2;

    @BeforeEach
    public void setup() {
        product1 = new Product();
        product1.setName("Product1");
        product1.setPrice(BigInteger.valueOf(100));
        product1.setQuantity(50);

        product2 = new Product();
        product2.setName("Product2");
        product2.setPrice(BigInteger.valueOf(200));
        product2.setQuantity(30);

        product1 = productRepository.save(product1);
        product2 = productRepository.save(product2);

        orderItem1 = new OrderItem();
        orderItem1.setProduct(product1);
        orderItem1.setQuantity(10);
        orderItem1.setAmount(BigInteger.valueOf(1000));

        orderItem2 = new OrderItem();
        orderItem2.setProduct(product2);
        orderItem2.setQuantity(5);
        orderItem2.setAmount(BigInteger.valueOf(1000));

        repository.saveAll(Arrays.asList(orderItem1, orderItem2));
    }
    
    @AfterEach
    public void cleanup(){
        productRepository.deleteAll();
        repository.deleteAll();
    }

    @Test
    @DisplayName("Test 1: Find top products by amount")
    public void findTopProductsByAmount_thenReturnList() {
        List<Object[]> results = repository.findTopProductsByAmount();

        assertThat(results).isNotEmpty();
        Object[] topProduct = results.get(0);
        assertEquals("Product1", topProduct[0]);
        assertEquals(BigInteger.valueOf(1000), topProduct[1]);
    }

    @Test
    @DisplayName("Test 2: Find sold products")
    public void findSoldProducts_thenReturnList() {
        List<String> soldProducts = repository.findSoldProducts();
        assertThat(soldProducts).containsExactlyInAnyOrder("Product1", "Product2");
    }

    @Test
    @DisplayName("Test 3: find total quantity per product")
    public void findTotalQuantityPerProduct_thenReturnList() {
        List<Object[]> results = repository.findTotalQuantityPerProduct();

        Map<String, Long> quantityMap = new HashMap<>();
        for (Object[] result : results) {
            quantityMap.put((String) result[0], ((Number) result[1]).longValue());
        }

        assertThat(quantityMap).containsEntry("Product1", 10L);
        assertThat(quantityMap).containsEntry("Product2", 5L);
    }

    @Test
    @DisplayName("Test 4: Find total amount per product")
    public void findTotalAmountPerProduct_thenReturnList() {
        List<Object[]> results = repository.findTotalAmountPerProduct();

        Map<String, BigInteger> amountMap = new HashMap<>();
        for (Object[] result : results) {
            amountMap.put((String) result[0], (BigInteger) result[1]);
        }

        assertThat(amountMap).containsEntry("Product1", BigInteger.valueOf(1000));
        assertThat(amountMap).containsEntry("Product2", BigInteger.valueOf(1000));
    }

    @Test
    @DisplayName("Test 5: @PrePersist lifecycle method")
    public void prePersist_thenReturnNewCreateAndUpdateTime() {
        repository.flush();

        assertNotNull(orderItem1.getOrderItemId());
        assertNotNull(orderItem1.getCreatedTime());
        assertNotNull(orderItem1.getUpdatedTime());

        assertEquals(orderItem1.getCreatedTime().toLocalDate().toString(), LocalDateTime.now().toLocalDate().toString());
        assertEquals(orderItem1.getUpdatedTime().toLocalDate().toString(), LocalDateTime.now().toLocalDate().toString());
    }

    @Test
    @DisplayName("Test 6: @PostUpdate lifecycle method")
    public void postUpdate_thenReturnNewUpdateTime() {
        orderItem1.setQuantity(19);
        repository.save(orderItem1);

        repository.flush();

        assertNotNull(orderItem1.getUpdatedTime());
        assertEquals(orderItem1.getUpdatedTime().toLocalDate().toString(), LocalDateTime.now().toLocalDate().toString());
    }


}
