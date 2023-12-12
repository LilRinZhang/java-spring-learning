package com.example.shopping.repository;

import org.springframework.stereotype.Repository;

import com.example.shopping.entity.OrderItem;

@Repository
public interface OrderItemRepository {
    void insert(OrderItem orderItem);
}
