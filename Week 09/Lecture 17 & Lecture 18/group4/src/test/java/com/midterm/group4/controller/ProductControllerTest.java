package com.midterm.group4.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigInteger;
import java.util.UUID;
import java.util.Arrays;
import org.mockito.Mockito;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.midterm.group4.data.model.Product;
import com.midterm.group4.service.impl.ProductServiceImpl;

import com.midterm.group4.dto.ProductMapperImpl;
import com.midterm.group4.exception.ObjectNotFoundException;


@WebMvcTest(controllers = ProductController.class)
@Import(ProductMapperImpl.class)
public class ProductControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ProductServiceImpl productService;

    private Product product;

    @BeforeEach
    private void setup(){
        product = new Product();
        product.setProductId(UUID.randomUUID());
        product.setName("MacBook Air 2020");
        product.setPrice(BigInteger.valueOf(190000000));
        product.setQuantity(10);
        product.setActive(false);
    }

    @AfterEach
    private void cleanup(){
        product = null;
    }

    @Test
    @DisplayName("Test 1: Get product by product Id")
    public void testGetProductByExistingId() throws Exception{
        Mockito.when(productService.findById(Mockito.any(UUID.class))).thenReturn(product);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/product/"+product.getProductId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.productId").value(product.getProductId().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("MacBook Air 2020"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.price").value("190000000"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.active").value(false));
    }

    @Test
    @DisplayName("Test 2: Get products sorted")
    public void testGetAllProductSorted() throws Exception {
        Product newProduct = new Product();
        newProduct.setProductId(UUID.randomUUID());
        newProduct.setName("MacBook Pro 2018");
        newProduct.setPrice(BigInteger.valueOf(9000000));
        newProduct.setQuantity(10);
        newProduct.setActive(false);
        
        Page<Product> pageProduct = new PageImpl<>(Arrays.asList(product, newProduct), PageRequest.of(0, 10), 2);
    
        Mockito.when(productService.findAllSorted(Mockito.any(Integer.class), Mockito.any(Integer.class), Mockito.any(String.class), Mockito.any(String.class))).thenReturn(pageProduct);
    
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/product")
                .param("sortOrder", "desc")
                .param("sortBy", "price"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].productId").value(product.getProductId().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].name").value("MacBook Air 2020"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[1].productId").value(newProduct.getProductId().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[1].name").value("MacBook Pro 2018"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[1].price").value(9000000));
    }

    @Test
    @DisplayName("Test 3: Set existing product status to active")
    public void testActivateProductStatus() throws Exception{
        Product activeProduct = new Product();
        activeProduct.setProductId(product.getProductId());
        activeProduct.setName("MacBook Air 2020");
        activeProduct.setPrice(BigInteger.valueOf(190000000));
        activeProduct.setQuantity(10);
        activeProduct.setActive(true);

        Mockito.when(productService.updateStatus(Mockito.any(UUID.class), Mockito.any(Boolean.class)))
            .thenReturn(activeProduct);
            
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/product/{id}/activate", product.getProductId())
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isAccepted())
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.jsonPath("$.productId").value(product.getProductId().toString()))
            .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("MacBook Air 2020"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.active").value(true));
    }

    @Test
    @DisplayName("Test 4: Set non existing product status to inactive")
    public void testDeactivateNonProductStatus() throws Exception {
        UUID nonExistingId = UUID.randomUUID();
        Mockito.when(productService.updateStatus(Mockito.eq(nonExistingId), Mockito.any(Boolean.class)))
            .thenThrow(new ObjectNotFoundException("Product not found with ID: " + nonExistingId));

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/product/{id}/deactivate", nonExistingId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Product not found with ID: " + nonExistingId));
    }

    @Test
    @DisplayName("Test 5: Get all products with by name and status")
    public void testSearchProduct() throws Exception {
        Product newProduct = new Product();
        newProduct.setProductId(UUID.randomUUID());
        newProduct.setName("MacBook Pro 2018");
        newProduct.setPrice(BigInteger.valueOf(9000000));
        newProduct.setQuantity(10);
        newProduct.setActive(false);
        
        Page<Product> pageProduct = new PageImpl<>(Arrays.asList(product, newProduct), PageRequest.of(0, 10), 2);
    
        Mockito.when(productService.findAllByQuery(Mockito.any(Integer.class), Mockito.any(Integer.class),
            Mockito.any(String.class), Mockito.any(String.class), Mockito.any(String.class), Mockito.any(String.class))).thenReturn(pageProduct);
    
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/product/search")
                .param("name", "MacBook")
                .param("status", "deactive"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].name").value(product.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[1].name").value(newProduct.getName()));
    }

    @Test
    @DisplayName("Test 6: Post new product")
    public void testPostProduct() throws Exception {
        Mockito.when(productService.save(Mockito.any(Product.class))).thenReturn(product);
    
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/product")
                .content(objectMapper.writeValueAsString(product))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isAccepted())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(product.getName()));
    }

    @Test
    @DisplayName("Test 7: Set existing product status to deactive")
    public void testDeactivateProductStatus() throws Exception{
        Product activeProduct = new Product();
        activeProduct.setProductId(product.getProductId());
        activeProduct.setName("MacBook Air 2020");
        activeProduct.setPrice(BigInteger.valueOf(190000000));
        activeProduct.setQuantity(10);
        activeProduct.setActive(false);

        Mockito.when(productService.updateStatus(Mockito.any(UUID.class), Mockito.any(Boolean.class)))
            .thenReturn(activeProduct);
            
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/product/{id}/deactivate", product.getProductId())
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isAccepted())
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.jsonPath("$.productId").value(product.getProductId().toString()))
            .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("MacBook Air 2020"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.active").value(false));
    }

    @Test
    @DisplayName("Test 8: Update product detail")
    public void testUpdateProductDetail() throws Exception{
        Product updatedProduct = new Product();
        updatedProduct.setProductId(product.getProductId());
        updatedProduct.setName("ASUS VivoBook");
        updatedProduct.setPrice(BigInteger.valueOf(20900900));
        updatedProduct.setQuantity(5);
        updatedProduct.setActive(true);

        Mockito.when(productService.update(Mockito.any(UUID.class), Mockito.any(Product.class)))
            .thenReturn(updatedProduct);
            
        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/product/{id}", product.getProductId())
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(updatedProduct)))
            .andExpect(MockMvcResultMatchers.status().isAccepted())
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.jsonPath("$.productId").value(product.getProductId().toString()))
            .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("ASUS VivoBook"))
            .andExpect(MockMvcResultMatchers.jsonPath("$.active").value(true))
            .andExpect(MockMvcResultMatchers.jsonPath("$.price").value(BigInteger.valueOf(20900900)));
    }

}
