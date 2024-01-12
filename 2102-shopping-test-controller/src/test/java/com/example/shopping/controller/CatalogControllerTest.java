package com.example.shopping.controller;

import static org.mockito.Mockito.doReturn;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.example.shopping.entity.Product;
import com.example.shopping.service.CatalogService;

@WebMvcTest(CatalogController.class)
public class CatalogControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    CatalogService catalogService;

    @Test
    void test_displayList() throws Exception {
        List<Product> products = new ArrayList<Product>();
        Product p1 = new Product();
        p1.setName("消しゴム");
        Product p2 = new Product();
        p2.setName("消しゴム２");
        products.add(p1);
        products.add(p2);

        doReturn(products).when(catalogService).findAll();

        mockMvc.perform(
                MockMvcRequestBuilders.get("/catalog/display-list"))
                .andExpect(status().isOk())
                .andExpect(view().name("catalog/productList"))
                .andExpect(content().string(containsString("消しゴム")))
                .andExpect(content().string(containsString("消しゴム２")))
                .andDo(print());

    }

    @Test
    void test_displayDetails() throws Exception {
        Product product = new Product();
        product.setName("消しゴム");
        doReturn(product).when(catalogService).findById("p01");

        mockMvc.perform(
                MockMvcRequestBuilders.get("/catalog/display-details").param("productId", "p01"))
                .andExpect(status().isOk())
                .andExpect(view().name("catalog/productDetails"))
                .andExpect(content().string(containsString("消しゴム")))
                .andDo(print());
    }
}
