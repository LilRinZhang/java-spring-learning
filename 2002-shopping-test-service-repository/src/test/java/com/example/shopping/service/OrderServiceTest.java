package com.example.shopping.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import com.example.shopping.entity.Order;
import com.example.shopping.enumeration.PaymentMethod;
import com.example.shopping.input.CartInput;
import com.example.shopping.input.CartItemInput;
import com.example.shopping.input.OrderInput;

@SpringBootTest(webEnvironment = WebEnvironment.NONE)
@Transactional
@Sql("OrderServiceTest.sql")
class OrderServiceTest {
    @Autowired
    OrderService orderService;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Test
    void test_placeOrder() {
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

        // 在庫数（前）
        int p01Stock_before = jdbcTemplate.queryForObject("SELECT stock FROM t_product WHERE id=?", Integer.class,
                "p01");
        int p02Stock_before = jdbcTemplate.queryForObject("SELECT stock FROM t_product WHERE id=?", Integer.class,
                "p02");

        Order order = orderService.placeOrder(orderInput, cartInput);

        Map<String, Object> orderMap = jdbcTemplate.queryForMap(
                "SELECT * FROM t_order WHERE id =?", order.getId());

        assertEquals("東京太郎", orderMap.get("customer_name"));
        assertEquals("東京都", orderMap.get("customer_address"));

        List<Map<String, Object>> orderItemMap = jdbcTemplate.queryForList(
                "SELECT * FROM t_order_item WHERE order_id=?", order.getId());
        assertEquals(2, orderItemMap.size());

        // 在庫数（後）
        int p01Stock = jdbcTemplate.queryForObject("SELECT stock FROM t_product WHERE id=?", Integer.class, "p01");
        int p02Stock = jdbcTemplate.queryForObject("SELECT stock FROM t_product WHERE id=?", Integer.class, "p02");
        assertEquals(3, p01Stock_before - p01Stock);
        assertEquals(4, p02Stock_before - p02Stock);

    }
}
