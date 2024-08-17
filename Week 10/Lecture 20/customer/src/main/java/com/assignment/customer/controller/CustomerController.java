package com.assignment.customer.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.assignment.customer.data.model.Customer;
import com.assignment.customer.service.CustomerService;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;



@RestController
@RequestMapping("/api/v1/customer")
public class CustomerController {
    
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService){
        this.customerService = customerService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable("id") Integer id) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(customerService.findById(id));
    }

    @GetMapping
    public ResponseEntity<List<Customer>> getAllCustomer(){
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(customerService.findAll());
    }

    @PostMapping
    public ResponseEntity<Customer> createNewCustomer(@RequestBody Customer customer){
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(customerService.save(customer));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable("id") Integer id, @RequestBody Customer entity) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(customerService.update(id, entity));
    }
    
}
