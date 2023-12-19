package com.example.shopping.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.example.shopping.entity.Product;
import com.example.shopping.service.ProductMaintenanceServiceImpl;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
public class ProductController {
    @Autowired
    private ProductMaintenanceServiceImpl service;

    @GetMapping("/products")
    public List<Product> getAllProducts() {
        return service.findAll();
    }

    @GetMapping("/product/{id}")
    public Product getProduct(@PathVariable String id) {
        return service.findById(id);
    }

}
