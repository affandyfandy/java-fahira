package com.assignment.customer.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.assignment.customer.data.model.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer>{
    
}
