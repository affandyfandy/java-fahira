package com.midterm.group4.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigInteger;
import java.util.UUID;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import com.midterm.group4.data.model.OrderItem;
import com.midterm.group4.data.model.Product;
import com.midterm.group4.dto.request.CreateOrderItemDTO;
import com.midterm.group4.dto.response.ReadOrderItemDTO;

public class OrderItemMapperTest {
    
    private OrderItemMapper orderItemMapper;

    @BeforeEach
    public void setUp() {
        orderItemMapper = Mappers.getMapper(OrderItemMapper.class);
    }

    @Test
    public void testToEntity() {
        // Setup
        CreateOrderItemDTO dto = new CreateOrderItemDTO();
        dto.setProductId(UUID.randomUUID());
        dto.setQuantity(5);

        // Act
        OrderItem orderItem = orderItemMapper.toEntity(dto);

        // Assert
        assertEquals(dto.getProductId(), orderItem.getProduct().getProductId());
        assertEquals(dto.getQuantity(), orderItem.getQuantity());
    }

    @Test
    public void testToListEntity() {
        // Setup
        CreateOrderItemDTO dto1 = new CreateOrderItemDTO();
        dto1.setProductId(UUID.randomUUID());
        dto1.setQuantity(5);

        CreateOrderItemDTO dto2 = new CreateOrderItemDTO();
        dto2.setProductId(UUID.randomUUID());
        dto2.setQuantity(10);

        List<CreateOrderItemDTO> dtoList = List.of(dto1, dto2);

        // Act
        List<OrderItem> orderItems = orderItemMapper.toListEntity(dtoList);

        // Assert
        assertEquals(2, orderItems.size());
        assertEquals(dto1.getProductId(), orderItems.get(0).getProduct().getProductId());
        assertEquals(dto1.getQuantity(), orderItems.get(0).getQuantity());
        assertEquals(dto2.getProductId(), orderItems.get(1).getProduct().getProductId());
        assertEquals(dto2.getQuantity(), orderItems.get(1).getQuantity());
    }
}
