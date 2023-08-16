package com.joliest.portfolios.groceryapi.controller;


import com.joliest.portfolios.groceryapi.model.Product;
import com.joliest.portfolios.groceryapi.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/v1/products")
public class ProductController {

    private final ProductService productService;
    @GetMapping
    public List<Product> getProducts() {
        return productService.getProducts();
    }
}
