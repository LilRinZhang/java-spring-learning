package com.example.shopping.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import com.example.shopping.entity.Product;

import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

@SpringBootTest(webEnvironment = WebEnvironment.NONE)
@Transactional
@Sql("CatalogServiceTest.sql")
public class CatalogServiceTest {
    @Autowired
    CatalogService catalogService;

    @Test
    public void test_findAll() {
        List<Product> products = catalogService.findAll();
        assertEquals(5, products.size());
    }

    @Test
    public void test_findById() {
        Product product = catalogService.findById("p05");
        assertEquals("pname05", product.getName());
        assertEquals(500, product.getPrice());
        assertEquals(50, product.getStock());
    }
}
