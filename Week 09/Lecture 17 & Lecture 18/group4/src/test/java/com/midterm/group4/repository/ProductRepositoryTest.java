package com.midterm.group4.repository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.midterm.group4.data.model.Product;
import com.midterm.group4.data.repository.ProductRepository;

import java.math.BigInteger;
import java.util.Optional;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest
@Transactional(propagation = Propagation.NOT_SUPPORTED)
public class ProductRepositoryTest {
    
    @Autowired
    private ProductRepository repository;

    private Product product;

    @BeforeEach
    public void setup() {
        product = new Product();
        product.setName("Mustang Shelby GT500");
        product.setPrice(BigInteger.valueOf(1090290200));
        product.setQuantity(50);
        product.setActive(true);
        repository.save(product);
    }

    @AfterEach
    public void teardown(){
        repository.delete(product);
    }

    @Test
    @DisplayName("Test 1: Create new product")
    public void createProduct_thenReturnProduct(){
        Product newProduct = new Product();
        newProduct.setName("McLaren Elva 2020");
        newProduct.setPrice(BigInteger.valueOf(100099023));
        newProduct.setQuantity(2);
        Product result = repository.save(newProduct);
        Optional<Product> actual = repository.findById(result.getProductId());
        assertTrue(actual.isPresent());
        assertEquals(result.getName(), actual.get().getName());
        assertEquals(result.getPrice(), actual.get().getPrice());
        assertEquals(result.getQuantity(), actual.get().getQuantity());
    }

    @Test
    @DisplayName("Test 2: Find product by existing Id")
    public void findProduct_withExistingProductId_thenReturnProduct(){
        Optional<Product> actual = repository.findById(product.getProductId());
        assertTrue(actual.isPresent());
        assertEquals(product.getName(), actual.get().getName());
        assertEquals(product.getPrice(), actual.get().getPrice());
        assertEquals(product.getQuantity(), actual.get().getQuantity());
        assertEquals(product.getProductId(), actual.get().getProductId());
    }

    @Test
    @DisplayName("Test 3: Retrieve all products from its status")
    public void retrieveProductByStatus_withActiveStatus_thenReturnPageOfProducts(){
        Product newProduct = new Product();
        newProduct.setName("FERRARI PURO SANGUE");
        newProduct.setPrice(BigInteger.valueOf(19828392));
        newProduct.setActive(false);
        newProduct.setQuantity(50);
        repository.save(newProduct);

        Pageable pageable = PageRequest.of(0, 10);
        Page<Product> result = repository.findAllByStatus(true, pageable);

        assertThat(result).isNotNull();
        assertEquals(result.getContent().size(), 1);
        assertTrue(result.getContent().get(0).isActive());
    }

    @Test
    @DisplayName("Test 4: Retrieve all products from its name")
    public void retrieveProductByName_withNameMustang_thenReturnPageOfProducts(){
        Product newProduct = new Product();
        newProduct.setName("Mustang Dark Horse");
        newProduct.setPrice(BigInteger.valueOf(19829183));
        newProduct.setActive(false);
        newProduct.setQuantity(55);
        repository.save(newProduct);

        Pageable pageable = PageRequest.of(0, 10);
        Page<Product> result = repository.findAllByName("Mustang", pageable);

        assertThat(result).isNotNull();
        assertEquals(result.getContent().size(), 2);
        assertEquals("Mustang Shelby GT500", result.getContent().get(0).getName());
    }

    @Test
    @DisplayName("Test 5: Retrieve all products from its name and status")
    public void retrieveProductByNameAndStatus_withNameAndStatusActive_thenReturnPageOfProducts(){
        Pageable pageable = PageRequest.of(0, 10);
        Page<Product> result = repository.findAllByNameAndStatus("Mustang", true, pageable);

        assertThat(result).isNotNull();
        assertEquals(1, result.getContent().size());
        assertEquals(product.getName(), result.getContent().get(0).getName());
    }

    @Test
    @DisplayName("Test 6: @PrePersist lifecycle method")
    public void prePersist_thenReturnNewCreateAndUpdateTime() {
        // Flush and clear to ensure @PrePersist is executed
        repository.flush();

        assertNotNull(product.getProductId());
        assertNotNull(product.getCreatedTime());
        assertNotNull(product.getUpdatedTime());

        // Validate that createdTime and updatedTime are set to the current time
        assertEquals(product.getCreatedTime().toLocalDate().toString(), LocalDateTime.now().toLocalDate().toString());
        assertEquals(product.getUpdatedTime().toLocalDate().toString(), LocalDateTime.now().toLocalDate().toString());
    }

    @Test
    @DisplayName("Test 7: @PostUpdate lifecycle method")
    public void postUpdate_thenReturnNewUpdateTime() {
        product.setQuantity(20); // Update the product to trigger @PostUpdate
        repository.save(product);

        // Flush and clear to ensure @PostUpdate is executed
        repository.flush();

        assertNotNull(product.getUpdatedTime());
        // Validate that updatedTime is updated to the current time
        assertEquals(product.getUpdatedTime().toLocalDate().toString(), LocalDateTime.now().toLocalDate().toString());
    }

}
