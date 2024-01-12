package com.example.shopping.controller;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.example.shopping.input.OrderInput;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import com.example.shopping.entity.Order;
import com.example.shopping.enumeration.PaymentMethod;
import com.example.shopping.input.CartInput;
import com.example.shopping.input.CartItemInput;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@Sql("OrderControllerIntegrationTest.sql")
class OrderControllerIntegrationTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Test
    void test_order() throws Exception {
        OrderInput orderInput = new OrderInput();
        orderInput.setName("東京太郎");
        orderInput.setAddress("東京都");
        orderInput.setPhone("090-0000-0000");
        orderInput.setEmailAddress("taro@example.com");
        orderInput.setPaymentMethod(PaymentMethod.CONVENIENCE_STORE);

        List<CartItemInput> cartItemInputs = new ArrayList<>();

        CartItemInput keshigom = new CartItemInput();
        keshigom.setProductId("p01");
        keshigom.setProductName("消しゴム");
        keshigom.setProductPrice(100);
        keshigom.setQuantity(3);
        cartItemInputs.add(keshigom);

        CartItemInput note = new CartItemInput();
        note.setProductId("p02");
        note.setProductName("ノート");
        note.setProductPrice(200);
        note.setQuantity(4);
        cartItemInputs.add(note);

        CartInput cartInput = new CartInput();
        cartInput.setCartItemInputs(cartItemInputs);

        OrderSession orderSession = new OrderSession();
        orderSession.setOrderInput(orderInput);
        orderSession.setCartInput(cartInput);

        MvcResult mvcResult = mockMvc.perform(
                post("/order/place-order").param("order", "").sessionAttr("scopedTarget.orderSession", orderSession))
                .andExpect(redirectedUrl("/order/display-completion")).andReturn();

        Order order = (Order) mvcResult.getFlashMap().get("order");

        Map<String, Object> orderMap = jdbcTemplate.queryForMap(
                "SELECT * FROM t_order WHERE id=?", order.getId());
        assertEquals("東京太郎", orderMap.get("customer_name"));
        assertEquals("東京都", orderMap.get("customer_address"));

    }

    @Test
    void test_displayStockShortagePage() throws Exception {
        OrderInput orderInput = new OrderInput();
        orderInput.setName("東京太郎");
        orderInput.setAddress("東京都");
        orderInput.setPhone("090-0000-0000");
        orderInput.setEmailAddress("taro@example.com");
        orderInput.setPaymentMethod(PaymentMethod.CONVENIENCE_STORE);

        List<CartItemInput> cartItemInputs = new ArrayList<>();

        CartItemInput keshigom = new CartItemInput();
        keshigom.setProductId("p01");
        keshigom.setProductName("消しゴム");
        keshigom.setProductPrice(100);
        keshigom.setQuantity(150);
        cartItemInputs.add(keshigom);

        CartInput cartInput = new CartInput();
        cartInput.setCartItemInputs(cartItemInputs);

        OrderSession orderSession = new OrderSession();
        orderSession.setOrderInput(orderInput);
        orderSession.setCartInput(cartInput);

        mockMvc.perform(
                post("/order/place-order").param("order", "").sessionAttr("scopedTarget.orderSession", orderSession))
                .andExpect(view().name("order/stockShortage"))
                .andExpect(content().string(containsString("在庫不足")));
    }
}