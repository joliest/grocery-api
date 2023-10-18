package com.joliest.portfolios.groceryapi.controller;


import com.joliest.portfolios.groceryapi.model.Product;
import com.joliest.portfolios.groceryapi.model.Products;
import com.joliest.portfolios.groceryapi.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/v1/products")
public class ProductController {

    private final ProductService productService;
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Product> getProducts() {
        return productService.getProducts();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public List<Product> addMultipleProducts(@RequestBody Products products) {
        return productService.addMultipleProducts(products);
    }
}
