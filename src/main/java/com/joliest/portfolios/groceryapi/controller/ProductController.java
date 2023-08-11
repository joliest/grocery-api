package com.joliest.portfolios.groceryapi.controller;


import com.joliest.portfolios.groceryapi.model.Product;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static java.util.Arrays.asList;

@RestController
@RequestMapping("/v1/products")
public class ProductController {
    @GetMapping
    public List<Product> getProducts() {
        return asList(Product.builder()
                .name("Gardenia")
                .price(82L)
                .store("SMP Supermarket")
                .category("Food")
                .subCategory("Bread")
                .datePurchased("02-23-2023")
                .link("2023-22-02-smp-supermarket")
                .build());
    }
}
