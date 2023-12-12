package com.example.shopping.repository;

import org.springframework.stereotype.Repository;

import com.example.shopping.entity.Order;

@Repository
public interface OrderRepository {
    void insert(Order order);
}
