package com.midterm.group4.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.midterm.group4.data.model.Product;
import com.midterm.group4.data.repository.ProductRepository;
import com.midterm.group4.exception.ObjectNotFoundException;
import com.midterm.group4.service.impl.ProductServiceImpl;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {
    
    @InjectMocks
	private ProductServiceImpl service;

	@Mock
	private ProductRepository repository;

    private Product product;

    @BeforeEach
    public void setup(){
        product = new Product();
        product.setProductId(UUID.randomUUID());
        product.setName("iPhone 13 Pro Max");
        product.setPrice(BigInteger.valueOf(982900000));
        product.setActive(true);
        product.setQuantity(10);
    }

    @AfterEach
    public void cleanup(){
        product = null;
    }

    @Test
    @DisplayName("Test 1: Retrieve all products sorted ASC")
    public void retrieveProduct_thenReturnPageProduct() {
        Sort sort = Sort.by(Sort.Direction.ASC, "name");
        Pageable pageable = PageRequest.of(0, 10, sort);
        Page<Product> productPage = new PageImpl<>(Arrays.asList(product), pageable, 1);
      
        when(repository.findAll(pageable)).thenReturn(productPage);
        
        Page<Product> actual = service.findAllSorted(0, 10, "name", "asc");

        assertEquals(1, actual.getTotalElements());
        assertEquals("iPhone 13 Pro Max", actual.getContent().get(0).getName());
        assertEquals(BigInteger.valueOf(982900000), actual.getContent().get(0).getPrice());
        assertEquals(true, actual.getContent().get(0).isActive());
        verify(repository, times(1)).findAll(pageable);
    }

    @Test
    @DisplayName("Test 2: Retrieve product by product Id")
    public void retrieveProduct_withProductId_thenReturnProduct() {
        when(repository.findById(product.getProductId())).thenReturn(Optional.of(product));

        Product actual = service.findById(product.getProductId());

        assertEquals("iPhone 13 Pro Max", actual.getName());
        assertEquals(BigInteger.valueOf(982900000), actual.getPrice());
        assertEquals(true, actual.isActive());
        
        verify(repository, times(1)).findById(product.getProductId());
    }

    @Test
    @DisplayName("Test 3: Create new product")
    public void createProduct_withValidData_thenRetunProduct() {
        Product newProduct = new Product();
        newProduct.setProductId(UUID.randomUUID());
        newProduct.setName("Samsung Z Flip");
        newProduct.setActive(true);
        newProduct.setPrice(BigInteger.valueOf(9800002));
        newProduct.setQuantity(10);

        when(repository.save(newProduct)).thenReturn(newProduct);

        Product actual = service.save(newProduct);

        assertEquals("Samsung Z Flip", actual.getName());
        assertEquals(10, actual.getQuantity());
        assertEquals(BigInteger.valueOf(9800002), actual.getPrice());
        assertEquals(true, actual.isActive());
        
        verify(repository, times(1)).save(newProduct);
    }

    @Test
    @DisplayName("Test 4: Update product detail")
    public void updateProduct_thenReturnProduct() {
        Product updatedProduct = new Product();
        updatedProduct.setName("Samsung Z Flip");
        updatedProduct.setActive(true);
        updatedProduct.setPrice(BigInteger.valueOf(9800002));
        updatedProduct.setQuantity(10);
    
        when(repository.findById(product.getProductId())).thenReturn(Optional.of(product));
        when(repository.save(any(Product.class))).thenAnswer(invocation -> invocation.getArgument(0));
    
        Product actual = service.update(product.getProductId(), updatedProduct);
    
        assertEquals("Samsung Z Flip", actual.getName());
        assertEquals(10, actual.getQuantity());
        assertEquals(BigInteger.valueOf(9800002), actual.getPrice());
        assertEquals(true, actual.isActive());
    
        verify(repository, times(1)).findById(product.getProductId());
        verify(repository, times(1)).save(any(Product.class));
    }

    @Test
    @DisplayName("Test 5: Update product status")
    public void updateProductStatus_withStatusToDeactive_thenReturnProduct() {
        Product updatedProduct = new Product();
        updatedProduct.setProductId(product.getProductId());
        updatedProduct.setName("iPhone 13 Pro Max");
        updatedProduct.setPrice(BigInteger.valueOf(982900000));
        updatedProduct.setQuantity(10);
        updatedProduct.setActive(false);

        when(repository.findById(product.getProductId())).thenReturn(Optional.of(product));
        when(repository.save(any(Product.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Product actual = service.updateStatus(product.getProductId(), false);

        assertEquals(false, actual.isActive());
        assertEquals(product.getProductId(), actual.getProductId());
        assertEquals("iPhone 13 Pro Max", actual.getName());
        assertEquals(BigInteger.valueOf(982900000), actual.getPrice());
        assertEquals(10, actual.getQuantity());
        
        verify(repository, times(1)).findById(product.getProductId());
        verify(repository, times(1)).save(any(Product.class));

    }

    @Test
    @DisplayName("Test 6: Save all products")
    public void saveListProducts_thenReturnVoid(){
        Product product1 = new Product();
        Product product2 = new Product();
        List<Product> products = Arrays.asList(product1, product2);
        when(repository.saveAll(any(List.class))).thenAnswer(invocation -> invocation.getArgument(0));
        service.saveAll(products);
        verify(repository, times(1)).saveAll(products);
    }

    @Test
    @DisplayName("Test 7: Update non existing product status")
    public void updateStatus_withNonExistProduct_thenReturnException(){
        UUID random = UUID.randomUUID();
        when(repository.findById(random)).thenReturn(Optional.empty());
        ObjectNotFoundException thrown = assertThrows(ObjectNotFoundException.class, () -> {
            service.updateStatus(random, false);
        });
        assertEquals("Product not found with ID: " + random, thrown.getMessage());
        verify(repository, times(1)).findById(random);
    }

    @Test
    @DisplayName("Test 8: Find all products by name")
    public void findAllByQuery_nameOnly_thenRetunPageProduct() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by(Sort.Direction.ASC, "name"));
        Page<Product> productPage = new PageImpl<>(Arrays.asList(new Product(), new Product()));

        when(repository.findAllByName(eq("testName"), eq(pageable)))
                .thenReturn(productPage);

        Page<Product> result = service.findAllByQuery(0, 10, "asc", "name", "testName", null);

        verify(repository, times(1)).findAllByName(eq("testName"), eq(pageable));
        assertEquals(productPage, result);
    }

    @Test
    @DisplayName("Test 9: Find all products by status")
    public void findAllByQuery_statusOnly_thenReturnPageProduct() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by(Sort.Direction.ASC, "name"));
        Page<Product> productPage = new PageImpl<>(Arrays.asList(new Product(), new Product()));

        when(repository.findAllByStatus(eq(true), eq(pageable)))
                .thenReturn(productPage);

        Page<Product> result = service.findAllByQuery(0, 10, "asc", "name", null, "active");

        verify(repository, times(1)).findAllByStatus(eq(true), eq(pageable));
        assertEquals(productPage, result);
    }

    @Test
    @DisplayName("Test 10: Find all products by name and status")
    public void findAllByQuery_nameAndStatus_thenReturnPageProduct() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by(Sort.Direction.ASC, "name"));
        Page<Product> productPage = new PageImpl<>(Arrays.asList(new Product(), new Product()));

        when(repository.findAllByNameAndStatus(eq("testName"), eq(true), eq(pageable)))
                .thenReturn(productPage);

        Page<Product> result = service.findAllByQuery(0, 10, "asc", "name", "testName", "active");

        verify(repository, times(1)).findAllByNameAndStatus(eq("testName"), eq(true), eq(pageable));
        assertEquals(productPage, result);
    }

    @Test
    @DisplayName("Test 4: Find all products with no name and no status")
    public void findAllByQuery_noNameNoStatus_thenRetunPageProduct() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by(Sort.Direction.ASC, "name"));
        Page<Product> productPage = new PageImpl<>(Arrays.asList(new Product(), new Product()));

        when(repository.findAll(eq(pageable)))
                .thenReturn(productPage);

        Page<Product> result = service.findAllByQuery(0, 10, "asc", "name", null, null);

        verify(repository, times(1)).findAll(eq(pageable));
        assertEquals(productPage, result);
    }


}
