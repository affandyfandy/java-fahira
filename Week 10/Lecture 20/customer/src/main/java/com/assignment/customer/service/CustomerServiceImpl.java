package com.assignment.customer.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.assignment.customer.data.model.Customer;
import com.assignment.customer.data.repository.CustomerRepository;

@Service
public class CustomerServiceImpl implements CustomerService{

    private final CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository){
        this.customerRepository = customerRepository;
    }

    @Override
    public Customer save(Customer customer) {
        return customerRepository.save(customer);
    }

    @Override
    public Customer update(Integer id, Customer customer) {
        Customer findCustomer = findById(id);
        if (findCustomer != null){
            findCustomer.setName(customer.getName() != null ? customer.getName() : findCustomer.getName());
            findCustomer.setDob(customer.getDob() != null ? customer.getDob() : findCustomer.getDob());
        }
        return customerRepository.save(findCustomer);
    }

    @Override
    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    @Override
    public Customer findById(Integer id) {
        Optional<Customer> optCust = customerRepository.findById(id);
        if (optCust.isPresent()) return optCust.get();
        return null;
    }
    
}
