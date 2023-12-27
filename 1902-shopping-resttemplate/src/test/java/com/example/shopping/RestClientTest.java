package com.example.shopping;

import java.net.URI;

import org.junit.jupiter.api.Test;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.web.client.RestTemplate;

import com.example.shopping.entity.Product;
import com.example.shopping.input.ProductMaintenanceInput;

public class RestClientTest {
    @Test
    public void test() {
        RestTemplate restTemplate = new RestTemplateBuilder().rootUri("http://localhost:8080").build();

        ProductMaintenanceInput productMaintenanceInput = new ProductMaintenanceInput();

        productMaintenanceInput.setName("Test");
        productMaintenanceInput.setPrice(200);
        productMaintenanceInput.setStock(10);

        URI location = restTemplate.postForLocation("/api/products", productMaintenanceInput);

        Product product = restTemplate.getForObject(location, Product.class);
        System.out.println(product.getName());
        System.out.println(product.getPrice());
        System.out.println(product.getStock());
        productMaintenanceInput.setName("TestForChange");
        restTemplate.put(location, productMaintenanceInput);
        restTemplate.delete(location);
    }
}
