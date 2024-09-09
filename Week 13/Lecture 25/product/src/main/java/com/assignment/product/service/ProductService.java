package com.assignment.product.service;

import java.util.List;

import com.assignment.product.data.model.Product;

public interface ProductService {
    
    Product save(Product product);
    Product findById(Integer id);
    Product update(Integer id, Product product);
    List<Product> findAll();
}
