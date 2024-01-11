package com.example.shopping.service;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.OptimisticLockingFailureException;

import com.example.shopping.entity.Product;
import com.example.shopping.input.ProductMaintenanceInput;
import com.example.shopping.repository.ProductRepository;

@ExtendWith(MockitoExtension.class)
class ProductMaintenanceServiceImplTest {
    @InjectMocks
    ProductMaintenanceServiceImpl productMaintenanceService;

    @Mock
    ProductRepository productRepository;

    @Test
    public void test_update() {
        ProductMaintenanceInput productMaintenanceInput = new ProductMaintenanceInput();
        productMaintenanceInput.setId("p01");
        productMaintenanceInput.setName("pname01");
        productMaintenanceInput.setPrice(100);
        productMaintenanceInput.setStock(10);

        ArgumentCaptor<Product> productCaptor = ArgumentCaptor.forClass(Product.class);
        doReturn(true).when(productRepository).update(productCaptor.capture());

        productMaintenanceService.update(productMaintenanceInput);

        Product product = productCaptor.getValue();
        assertEquals("pname01", product.getName());
        assertEquals(100, product.getPrice());
        assertEquals(10, product.getStock());
    }

    @Test
    public void test_update_更新に失敗() {
        doReturn(false).when(productRepository).update(any());
        ProductMaintenanceInput productMaintenanceInput = new ProductMaintenanceInput();

        assertThatThrownBy(() -> {
            productMaintenanceService.update(productMaintenanceInput);
        }).isInstanceOf(OptimisticLockingFailureException.class);
    }

    @Test
    public void test_findAll() {
        Product product1 = new Product();
        product1.setName("消しゴム1");
        Product product2 = new Product();
        product2.setName("消しゴム2");
        List<Product> products = new ArrayList<Product>();
        products.add(product1);
        products.add(product2);
        doReturn(products).when(productRepository).selectAll();

        List<Product> actuaList = productMaintenanceService.findAll();
        assertEquals(2, actuaList.size());
    }

    @Test
    public void test_findById() {
        Product product = new Product();
        product.setName("消しゴム");
        product.setPrice(10);
        product.setStock(10);
        doReturn(product).when(productRepository).selectById("p01");

        Product actual = productMaintenanceService.findById("p01");
        assertEquals("消しゴム", actual.getName());
        assertEquals(10, actual.getPrice());
        assertEquals(10, actual.getStock());
    }
}
