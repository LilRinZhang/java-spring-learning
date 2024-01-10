package com.example.shopping.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import com.example.shopping.entity.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;

@JdbcTest
@Sql("JdbcProductRepositoryTest.sql")
public class JdbcProductRepositoryTest {
    @Autowired
    JdbcTemplate jdbcTemplate;

    ProductRepository productRepository;

    @BeforeEach
    void setUp() {
        productRepository = new JdbcProductRepository(jdbcTemplate);
    }

    @Test
    void test_selectAll() {
        List<Product> products = productRepository.selectAll();
        assertEquals(5, products.size());
    }

    @Test
    void test_selectById() {
        Product product = productRepository.selectById("p05");
        assertEquals("pname05", product.getName());
        assertEquals(500, product.getPrice());
        assertEquals(50, product.getStock());
    }

    @Test
    void test_update() {
        Product productBefore = productRepository.selectById("p05");
        assertEquals("pname05", productBefore.getName());
        assertEquals(500, productBefore.getPrice());
        assertEquals(50, productBefore.getStock());

        Product productAfter = new Product();
        productAfter.setId(productBefore.getId());
        productAfter.setName("pname05_update");
        productAfter.setPrice(0);
        productAfter.setStock(0);
        productRepository.update(productAfter);
        Product product = productRepository.selectById("p05");
        assertEquals("pname05_update", product.getName());
        assertEquals(0, product.getPrice());
        assertEquals(0, product.getStock());

    }

}
