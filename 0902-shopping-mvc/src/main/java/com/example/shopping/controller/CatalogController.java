package com.example.shopping.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.shopping.entity.Product;
import com.example.shopping.service.CatalogService;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/catalog")
public class CatalogController {
    private final CatalogService catalogService;

    public CatalogController(CatalogService catalogService) {
        this.catalogService = catalogService;
    }

    @GetMapping("/display-list")
    public String displayList(Model model) {
        List<Product> productList = catalogService.findAll();
        model.addAttribute("productList", productList);
        return "catalog/productList";
    }

    @GetMapping("/display-details")
    public String displayDetail(@RequestParam String productId, Model model) {
        Product product = catalogService.findById(productId);
        model.addAttribute("product", product);
        return "catalog/details";
    }

}
