package com.example.shopping.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.example.shopping.entity.Order;
import com.example.shopping.service.OrderMaintenanceServiceImpl;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
public class OrderController {
    @Autowired
    private OrderMaintenanceServiceImpl service;

    @GetMapping("/order")
    public List<Order> getAllOrder() {
        return service.findAll();
    }

    @GetMapping("/order/{id}")
    public Order getOrder(@PathVariable String id) {
        return service.findById(id);
    }

}
