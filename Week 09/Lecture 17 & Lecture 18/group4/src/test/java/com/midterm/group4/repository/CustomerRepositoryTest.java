package com.midterm.group4.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.bouncycastle.util.test.FixedSecureRandom.BigInteger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.time.LocalDateTime;

import com.midterm.group4.data.model.Customer;
import com.midterm.group4.data.model.Invoice;
import com.midterm.group4.data.repository.CustomerRepository;

@DataJpaTest
@Transactional(propagation = Propagation.NOT_SUPPORTED)
public class CustomerRepositoryTest {

    @Autowired
    private CustomerRepository repository;

    private Customer customer;

    @BeforeEach
    public void setup() {
        customer = new Customer();
        customer.setFirstName("Syafiq");
        customer.setLastName("Kyle");
        customer.setPhone("08187293827");
        repository.save(customer);
    }

    @AfterEach
    public void teardown(){
        repository.delete(customer);
    }
    
    @Test
    @DisplayName("Test 1: Find customer by existing Id")
    public void getCustomer_withExistingId_thenReturnCustomer(){
        Optional<Customer> actual = repository.findById(customer.getCustomerId());
        assertThat(actual).isNotNull();
        assertEquals(actual.get().getFirstName(), "Syafiq");
        assertEquals(actual.get().getLastName(), "Kyle");
        assertEquals(actual.get().getPhone(), "08187293827");
    }

    @Test
    @DisplayName("Test 2: Delete customer data by existing Id")
    public void deleteCustomer_withExistingId_thenReturnCustomer(){
        repository.delete(customer);
        Optional<Customer> actual = repository.findById(customer.getCustomerId());
        assertThat(actual).isEmpty();
    }

    @Test
    @DisplayName("Test 3: @PrePersist lifecycle method")
    public void prePersist_thenReturnNewCreateAndUpdateTime() {
        repository.flush();

        assertNotNull(customer.getCustomerId());
        assertNotNull(customer.getCreatedTime());
        assertNotNull(customer.getUpdatedTime());

        assertEquals(customer.getCreatedTime().toLocalDate().toString(), LocalDateTime.now().toLocalDate().toString());
        assertEquals(customer.getUpdatedTime().toLocalDate().toString(), LocalDateTime.now().toLocalDate().toString());
    }

    @Test
    @DisplayName("Test 4: @PostUpdate lifecycle method")
    public void postUpdate_thenReturnNewUpdateTime() {
        customer.setLastName("Maurer");
        repository.save(customer);

        repository.flush();

        assertNotNull(customer.getUpdatedTime());
        assertEquals(customer.getUpdatedTime().toLocalDate().toString(), LocalDateTime.now().toLocalDate().toString());
    }
}
