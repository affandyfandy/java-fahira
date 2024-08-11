package com.midterm.group4.controller;

import java.util.UUID;

import java.util.Arrays;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
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
import com.midterm.group4.data.model.Customer;
import com.midterm.group4.dto.CustomerMapperImpl;
import com.midterm.group4.service.impl.CustomerServiceImpl;

import com.midterm.group4.exception.ObjectNotFoundException;

@WebMvcTest(controllers = CustomerController.class)
@Import(CustomerMapperImpl.class)
public class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerServiceImpl service;

    @Autowired
    private ObjectMapper objectMapper;

    private Customer customer;

    @BeforeEach
    public void setup() {
        customer = new Customer();
        customer.setCustomerId(UUID.randomUUID());
        customer.setFirstName("Tirta");
        customer.setLastName("Cipeng");
        customer.setPhone("081809027865");
        customer.setActive(true);
    }

    @AfterEach
    public void cleanup(){
        customer = null;
    }

    @Test
    @DisplayName("Test 1: Create new customer")
    public void testCreateCustomer() throws Exception {
        Mockito.when(service.saveCustomer(Mockito.any(Customer.class))).thenReturn(customer);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/customer")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(customer)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("Tirta"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value("Cipeng"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.phone").value("081809027865"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.active").value(true));
    }
    
    @Test
    @DisplayName("Test 2: Set non existing customer status to deactive")
    public void testDeactivateStatusFailed() throws Exception {
        UUID customerId = UUID.randomUUID();
        
        Mockito.when(service.updateStatusNew(Mockito.any(UUID.class), Mockito.any(Boolean.class)))
            .thenThrow(new ObjectNotFoundException("Customer not found with ID: " + customerId));
            
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/customer/{id}/deactivate", customerId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Customer not found with ID: " + customerId));
    }

    @Test
    @DisplayName("Test 3: Update existing customer detail")
    public void testUpdateCustomer() throws Exception {
        Customer updatedCustomer = new Customer();
        updatedCustomer.setCustomerId(customer.getCustomerId());
        updatedCustomer.setFirstName("Fahira");
        updatedCustomer.setLastName("Adindiah");
        updatedCustomer.setPhone("081807099909");
        updatedCustomer.setActive(false);
        
        Mockito.when(service.updateNew(Mockito.eq(customer.getCustomerId()), Mockito.any(Customer.class)))
            .thenReturn(updatedCustomer);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/customer/{id}",customer.getCustomerId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedCustomer)))
                .andExpect(MockMvcResultMatchers.status().isAccepted())
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("Fahira"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value("Adindiah"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.phone").value("081807099909"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.active").value(false));
    }

    @Test
    @DisplayName("Test 4: Get customer by customer Id")
    public void testGetCustomer() throws Exception{
        Mockito.when(service.findById(Mockito.eq(customer.getCustomerId()))).thenReturn(customer);
        
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/customer/{id}",customer.getCustomerId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("Tirta"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value("Cipeng"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.phone").value("081809027865"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.active").value(true));
    }

    @Test
    @DisplayName("Test 5: Get all customers")
    public void testGetAllCustomers() throws Exception{
        Customer newCustomer = new Customer();
        newCustomer.setCustomerId(UUID.randomUUID());
        newCustomer.setFirstName("Nabila");
        newCustomer.setLastName("Syakieb");
        newCustomer.setPhone("0818928738122");
        newCustomer.setActive(false);

        Page<Customer> pageCustomer = new PageImpl<>(Arrays.asList(customer, newCustomer), PageRequest.of(0, 10), 2);

        Mockito.when(service.findAll(Mockito.any(Integer.class), Mockito.any(Integer.class))).thenReturn(pageCustomer);
        
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/customer"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].firstName").value("Tirta"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[1].firstName").value("Nabila"));
    }

    @Test
    @DisplayName("Test 6: Activate existing customer status")
    public void testUpdateCustomerStatus() throws Exception{
        customer.setActive(true);
        Mockito.when(service.updateStatusNew(Mockito.eq(customer.getCustomerId()), Mockito.eq(true))).thenReturn(customer);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/customer/{id}/activate", customer.getCustomerId()))
            .andExpect(MockMvcResultMatchers.status().isAccepted())
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.jsonPath("$.active").value(true));
    }

    @Test
    @DisplayName("Test 7: Successfully deactivate customer status")
    public void testDeactivateStatusSuccess() throws Exception {
        customer.setActive(false);
        
        Mockito.when(service.updateStatusNew(Mockito.eq(customer.getCustomerId()), Mockito.eq(false)))
                .thenReturn(customer);
        
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/customer/{id}/deactivate", customer.getCustomerId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isAccepted())
                .andExpect(MockMvcResultMatchers.jsonPath("$.customerId").value(customer.getCustomerId().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("Tirta"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value("Cipeng"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.active").value(false));
    }
}
