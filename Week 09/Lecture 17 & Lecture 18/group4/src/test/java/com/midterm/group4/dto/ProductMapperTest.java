package com.midterm.group4.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigInteger;
import java.util.UUID;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import com.midterm.group4.data.model.Product;
import com.midterm.group4.dto.request.CreateProductDTO;
import com.midterm.group4.dto.response.ReadProductDTO;

public class ProductMapperTest {
    
    private ProductMapper productMapper;

    @BeforeEach
    public void setUp() {
        productMapper = Mappers.getMapper(ProductMapper.class);
    }

    @Test
    public void testToEntity() {
        // Setup
        CreateProductDTO dto = new CreateProductDTO();
        dto.setName("Product 1");
        dto.setPrice(BigInteger.valueOf(200));
        dto.setQuantity(15);

        // Act
        Product product = productMapper.toEntity(dto);

        // Assert
        assertEquals(dto.getName(), product.getName());
        assertEquals(dto.getPrice(), product.getPrice());
        assertEquals(dto.getQuantity(), product.getQuantity());
    }

    @Test
    public void testToReadProductDto() {
        // Setup
        Product product = new Product();
        product.setProductId(UUID.randomUUID());
        product.setPrice(BigInteger.valueOf(200));
        product.setQuantity(15);

        // Act
        ReadProductDTO dto = productMapper.toReadProductDto(product);

        // Assert
        assertEquals(product.getProductId(), dto.getProductId());
        assertEquals(product.getPrice(), dto.getPrice());
    }

    @Test
    public void testToListReadProductDto() {
        // Setup
        Product product1 = new Product();
        product1.setProductId(UUID.randomUUID());
        product1.setPrice(BigInteger.valueOf(200));
        product1.setQuantity(15);

        Product product2 = new Product();
        product2.setProductId(UUID.randomUUID());
        product2.setPrice(BigInteger.valueOf(300));
        product2.setQuantity(20);

        List<Product> productList = List.of(product1, product2);

        // Act
        List<ReadProductDTO> dtoList = productMapper.toListReadProductDto(productList);

        // Assert
        assertEquals(2, dtoList.size());
        assertEquals(product1.getProductId(), dtoList.get(0).getProductId());
        assertEquals(product1.getPrice(), dtoList.get(0).getPrice());
        assertEquals(product2.getProductId(), dtoList.get(1).getProductId());
        assertEquals(product2.getPrice(), dtoList.get(1).getPrice());
    }
}
