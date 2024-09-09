package com.assignment.customer.service;

import java.util.List;
import com.assignment.customer.data.model.Customer;

public interface CustomerService {
    
    Customer save(Customer customer);
    Customer update(Integer id, Customer customer);
    List<Customer> findAll();
    Customer findById(Integer id);
}
