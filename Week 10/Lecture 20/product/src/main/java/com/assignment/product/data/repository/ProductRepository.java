package com.assignment.product.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.assignment.product.data.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer>{
    
}
