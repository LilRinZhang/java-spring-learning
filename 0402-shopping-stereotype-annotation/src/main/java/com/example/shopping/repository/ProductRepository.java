package com.example.shopping.repository;

import org.springframework.stereotype.Repository;

import com.example.shopping.entity.Product;

@Repository
public interface ProductRepository {
    Product selectById(String id);

    boolean update(Product product);
}
