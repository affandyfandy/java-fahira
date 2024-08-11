package com.midterm.group4.service;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;
import java.util.Arrays;

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

import com.midterm.group4.data.model.Customer;
import com.midterm.group4.data.repository.CustomerRepository;
import com.midterm.group4.exception.InvalidInputException;
import com.midterm.group4.exception.ObjectNotFoundException;
import com.midterm.group4.service.impl.CustomerServiceImpl;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest {
    
    @InjectMocks
	private CustomerServiceImpl service;

	@Mock
	private CustomerRepository repository;

    private Customer customer;

    @BeforeEach
    public void setup() {
        customer = new Customer();
        customer.setFirstName("Natasha");
        customer.setLastName("Abigail");
        customer.setPhone("081807036877");
        customer.setCustomerId(UUID.randomUUID());
        customer.setActive(true);
    }

    @AfterEach
    public void cleanup(){
        customer = null;
    }

    @Test
    @DisplayName("Test 1: Retrieve all customer")
    public void retrieveAllCustomers_thenReturnPageCustomers() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Customer> customerPage = new PageImpl<>(Arrays.asList(customer), pageable, 1);
        when(repository.findAll(pageable)).thenReturn(customerPage);
        Page<Customer> result = service.findAll(0, 10);
        assertEquals(1, result.getTotalElements());
        assertEquals("Natasha", result.getContent().get(0).getFirstName());
        verify(repository, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @DisplayName("Test 2: Find exisiting customer by customer Id")
    public void findById_withExistingId_thenReturnCustomer() {
        when(repository.findById(customer.getCustomerId())).thenReturn(Optional.of(customer));
        Customer result = service.findById(customer.getCustomerId());

        assertEquals("Natasha", result.getFirstName());
        assertEquals("Abigail", result.getLastName());
        assertEquals("081807036877", result.getPhone());
        assertEquals(true, result.isActive());
        
        verify(repository, times(1)).findById(customer.getCustomerId());
    }

    @Test
    @DisplayName("Test 3: Find non existing customer")
    public void findById_withNonExistingId_thenReturnException() {
        UUID costumerId = UUID.randomUUID();
        when(repository.findById(costumerId)).thenReturn(Optional.empty());
        assertThrows(ObjectNotFoundException.class, () -> {
            service.findById(costumerId);
        });
        verify(repository, times(1)).findById(costumerId);
    }

    @Test
    @DisplayName("Test 4: Update existing customer status")
    public void updateStatus_withExistingId_thenUpdateCustomerStatus() {
        Customer updatedCustomer = new Customer();
        updatedCustomer.setCustomerId(customer.getCustomerId());
        updatedCustomer.setFirstName("Natasha");
        updatedCustomer.setLastName("Abigail");
        updatedCustomer.setPhone("081807036877");
        updatedCustomer.setActive(false);

        when(repository.findById(customer.getCustomerId())).thenReturn(Optional.of(customer));
        when(repository.save(any(Customer.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Customer actual = service.updateStatusNew(customer.getCustomerId(), false);

        assertEquals(false, actual.isActive());
        assertEquals(customer.getCustomerId(), actual.getCustomerId());
        assertEquals("Natasha", actual.getFirstName());
        assertEquals("Abigail", actual.getLastName());
        assertEquals("081807036877", actual.getPhone());
        
        verify(repository, times(1)).findById(customer.getCustomerId());
        verify(repository, times(1)).save(any(Customer.class));
    }

    @Test
    @DisplayName("Test 5: Check validation customer phone number with invalid number")
    public void validatePhoneNumber_withInvalidData_thenReturnException(){
        String invalidNumber = "abc981882929";
        InvalidInputException thrown = assertThrows(InvalidInputException.class, () -> {
            service.validatePhoneNumber(invalidNumber);
        });
        assertEquals("Invalid phone number format.", thrown.getMessage());
    }   

    @Test
    @DisplayName("Test 6: Check validation customer phone number with null")
    public void validatePhoneNumber_withNull_thenReturnVoid(){
        InvalidInputException thrown = assertThrows(InvalidInputException.class, () -> {
            service.validatePhoneNumber("");
        });
        assertEquals("Phone number cannot be null or empty.", thrown.getMessage());
    }   

    // @Test
    // @DisplayName("Test 7: Update non existing customer")
    // public void updateCustomer_withNonExisitingId_thenReturnException(){
    //     UUID random = UUID.randomUUID();
    //     when(repository.findById(random)).thenReturn(null);
    //     ObjectNotFoundException thrown = assertThrows(ObjectNotFoundException.class, () -> {
    //         service.updateNew(random, customer);
    //     });
    //     assertEquals("Customer not found with ID: " + random, thrown.getMessage());
    //     verify(repository, times(1)).findById(random);
    // }   

    @Test
    @DisplayName("Test 8: Update existing customer detail")
    public void updateCustomer_withExisitingId_thenReturnCustomer(){
        Customer updatedCustomer = new Customer();
        updatedCustomer.setCustomerId(customer.getCustomerId());
        updatedCustomer.setFirstName("Mario");
        updatedCustomer.setLastName("Maurer");
        updatedCustomer.setPhone("081892737333");
        updatedCustomer.setActive(true);

        when(repository.findById(customer.getCustomerId())).thenReturn(Optional.of(customer));
        Customer actual = service.updateNew(updatedCustomer.getCustomerId(), updatedCustomer);
        assertEquals(updatedCustomer, actual);
        verify(repository, times(1)).findById(customer.getCustomerId());
    }   

    @Test
    @DisplayName("Test 9: Save customer with invalid phone number")
    public void saveCustomer_invalidPhoneNumber_thenReturnException() {
        Customer newCustomer = new Customer();
        newCustomer.setPhone("invalid_phone");

        InvalidInputException thrown = assertThrows(InvalidInputException.class, () -> {
            service.saveCustomer(newCustomer);
        });
        assertThrows(InvalidInputException.class, () -> {
            service.saveCustomer(newCustomer);
        });
        assertEquals("Invalid phone number format.", thrown.getMessage());
        verify(repository, never()).save(any(Customer.class));
    }

    @Test
    @DisplayName("Test 10: Save customer")
    public void saveCustomer_withValidData_thenReturnCustomer() {
        when(repository.save(any(Customer.class))).thenReturn(customer);
        Customer savedCustomer = service.saveCustomer(customer);
        assertTrue(savedCustomer.isActive());
        assertNotNull(savedCustomer.getListInvoice());
        assertEquals(LocalDateTime.now().getDayOfYear(), savedCustomer.getCreatedTime().getDayOfYear());
        assertEquals(LocalDateTime.now().getDayOfYear(), savedCustomer.getUpdatedTime().getDayOfYear());
        verify(repository, times(1)).save(customer);
    }

    // @Test
    // @DisplayName("Test 11: Update status with non existing id")
    // public void updateStatus_withNonExistingId_thenRetunCustomerNotFound() {
    //     UUID random = UUID.randomUUID();
    //     when(repository.findById(random)).thenReturn(null);

    //     ObjectNotFoundException thrown = assertThrows(ObjectNotFoundException.class, () -> {
    //         service.updateStatusNew(random, false);
    //     });

    //     assertEquals("Customer not found with ID: " + random, thrown.getMessage());

    //     verify(repository, times(1)).findById(random);
    //     verify(repository, never()).save(any(Customer.class));
    // }
}
