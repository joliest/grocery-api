package com.joliest.portfolios.groceryapi.controller;


import com.joliest.portfolios.groceryapi.model.Product;
import com.joliest.portfolios.groceryapi.model.ProductImport;
import com.joliest.portfolios.groceryapi.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("/v1/products")
public class ProductController {

    private final ProductService productService;
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Product> getProducts(@RequestParam Optional<String> search, Pageable pageable) {
        return productService.getProducts(search, pageable);
    }

    @PostMapping("/import")
    @ResponseStatus(HttpStatus.CREATED)
    public List<ProductImport> importMultipleProducts(@RequestBody List<ProductImport> productImportList) {
        return productService.importMultipleProducts(productImportList);
    }
}
