package com.assignment.product.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.assignment.product.data.model.Product;
import com.assignment.product.data.repository.ProductRepository;

@Service
public class ProductServiceImpl implements ProductService{

    public final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository){
        this.productRepository = productRepository;
    }

    @Override
    public Product save(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Product findById(Integer id) {
        Optional<Product> optProduct = productRepository.findById(id);
        if (optProduct.isPresent()) return optProduct.get();
        return null;
    }

    @Override
    public Product update(Integer id, Product product) {
        Product findProduct = findById(id);
        if (findProduct != null){
            findProduct.setName(product.getName() != null ? product.getName() : findProduct.getName());
            findProduct.setQuantity(product.getQuantity() != null ? product.getQuantity() : findProduct.getQuantity());
            findProduct.setPrice(product.getPrice() != null ? product.getPrice() : findProduct.getPrice());
        }
        return productRepository.save(findProduct);
    }

    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }
    
}
