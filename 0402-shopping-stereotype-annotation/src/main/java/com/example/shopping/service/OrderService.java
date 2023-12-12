package com.example.shopping.service;

import org.springframework.stereotype.Service;

import com.example.shopping.entity.Order;
import com.example.shopping.input.CartInput;
import com.example.shopping.input.OrderInput;

@Service
public interface OrderService {
    Order placeOrder(OrderInput orderInput, CartInput cartInput);
}
