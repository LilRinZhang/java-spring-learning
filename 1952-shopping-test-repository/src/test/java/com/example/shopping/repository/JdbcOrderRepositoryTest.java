package com.example.shopping.repository;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

import com.example.shopping.entity.Order;
import com.example.shopping.enumeration.PaymentMethod;

@JdbcTest
public class JdbcOrderRepositoryTest {
    @Autowired
    JdbcTemplate jdbcTemplate;

    OrderRepository orderRepository;

    @BeforeEach
    void setUp() {
        orderRepository = new JdbcOrderRepository(jdbcTemplate);
    }

    @Test
    void test_insert() {
        Order order = new Order();
        order.setId("1");
        order.setOrderDateTime(null);
        order.setBillingAmount(0);
        order.setCustomerName("Test");
        order.setCustomerAddress("Test");
        order.setCustomerPhone("Test");
        order.setCustomerEmailAddress("Test");
        order.setPaymentMethod(PaymentMethod.BANK);
        orderRepository.insert(order);

        Map<String, Object> orderMap = jdbcTemplate.queryForMap(
                "SELECT * FROM t_order WHERE id=?", "1");

        assertAll(
                () -> {
                    assertEquals(order.getOrderDateTime(), orderMap.get("order_date_time"));
                },
                () -> {
                    assertEquals(order.getBillingAmount(), orderMap.get("billing_amount"));
                },
                () -> {
                    assertEquals(order.getCustomerName(), orderMap.get("customer_name"));
                },
                () -> {
                    assertEquals(order.getCustomerAddress(), orderMap.get("customer_address"));
                },
                () -> {
                    assertEquals(order.getCustomerPhone(), orderMap.get("customer_phone"));
                },
                () -> {
                    assertEquals(order.getCustomerEmailAddress(), orderMap.get("customer_email_address"));
                },
                () -> {
                    assertEquals(order.getPaymentMethod().toString(), orderMap.get("payment_method"));
                });

    }
}
