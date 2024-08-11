package com.midterm.group4.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.UUID;
import java.util.List;
import java.util.ArrayList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import com.midterm.group4.data.model.Customer;
import com.midterm.group4.dto.request.CreateCustomerDTO;
import com.midterm.group4.dto.response.ReadCustomerDTO;
import com.midterm.group4.dto.response.ReadCustomerOrderDTO;

public class CustomerMapperTest {
    
    private CustomerMapper customerMapper;

    @BeforeEach
    public void setUp() {
        customerMapper = Mappers.getMapper(CustomerMapper.class);
    }

    @Test
    public void testToReadDto() {
        Customer customer = new Customer();
        customer.setCustomerId(UUID.randomUUID());
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customer.setPhone("123456789");

        ReadCustomerDTO dto = customerMapper.toReadDto(customer);

        assertEquals(customer.getCustomerId(), dto.getCustomerId());
        assertEquals(customer.getFirstName(), dto.getFirstName());
        assertEquals(customer.getLastName(), dto.getLastName());
        assertEquals(customer.getPhone(), dto.getPhone());
    }

    @Test
    public void testToEntity() {
        CreateCustomerDTO createCustomerDTO = new CreateCustomerDTO();
        createCustomerDTO.setFirstName("John");
        createCustomerDTO.setLastName("Doe");
        createCustomerDTO.setPhone("123456789");

        Customer customer = customerMapper.toEntity(createCustomerDTO);

        assertEquals(createCustomerDTO.getFirstName(), customer.getFirstName());
        assertEquals(createCustomerDTO.getLastName(), customer.getLastName());
        assertEquals(createCustomerDTO.getPhone(), customer.getPhone());
    }

    @Test
    public void testToListReadDto() {
        Customer customer1 = new Customer();
        customer1.setCustomerId(UUID.randomUUID());
        customer1.setFirstName("John");
        customer1.setLastName("Doe");
        customer1.setPhone("123456789");

        Customer customer2 = new Customer();
        customer2.setCustomerId(UUID.randomUUID());
        customer2.setFirstName("Jane");
        customer2.setLastName("Smith");
        customer2.setPhone("987654321");

        List<Customer> customers = new ArrayList<>();
        customers.add(customer1);
        customers.add(customer2);

        List<ReadCustomerDTO> dtos = customerMapper.toListReadDto(customers);

        assertEquals(2, dtos.size());
        assertEquals(customer1.getCustomerId(), dtos.get(0).getCustomerId());
        assertEquals(customer2.getCustomerId(), dtos.get(1).getCustomerId());
    }

    @Test
    public void testToReadCustomerOrderDto() {
        Customer customer = new Customer();
        customer.setCustomerId(UUID.randomUUID());
        customer.setFirstName("John");
        customer.setLastName("Doe");

        ReadCustomerOrderDTO dto = customerMapper.toReadCustomerOrderDto(customer);

        assertEquals(customer.getCustomerId(), dto.getCustomerId());
        assertEquals(customer.getFirstName(), dto.getFirstName());
        assertEquals(customer.getLastName(), dto.getLastName());
    }
}
