package com.joliest.portfolios.groceryapi.service;

import com.joliest.portfolios.groceryapi.domain.entity.ProductEntity;
import com.joliest.portfolios.groceryapi.domain.repository.ProductRepository;
import com.joliest.portfolios.groceryapi.model.Product;
import com.joliest.portfolios.groceryapi.model.Products;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static com.joliest.portfolios.groceryapi.utils.DateUtil.convertDateToDefaultFormat;
import static com.joliest.portfolios.groceryapi.utils.DateUtil.convertStrToLocalDateTime;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public List<Product> getProducts() {
        return productRepository.findAll().stream()
                .map(productEntity ->
                        Product.builder()
                                .id(productEntity.getId())
                                .name(productEntity.getName())
                                .price(productEntity.getPrice())
                                .store(productEntity.getStore())
                                .category(productEntity.getCategory())
                                .subCategory(productEntity.getSubCategory())
                                .link(productEntity.getLink())
                                .datePurchased(convertDateToDefaultFormat(productEntity.getDatePurchased()))
                                .build()
                ).collect(Collectors.toList());
    }

    public Product addProduct(Product product) {
        ProductEntity newProduct = convertProductToEntity(product);

        ProductEntity productEntity = productRepository.save(newProduct);
        return Product.builder()
                .id(productEntity.getId())
                .name(productEntity.getName())
                .price(productEntity.getPrice())
                .category(productEntity.getCategory())
                .subCategory(productEntity.getSubCategory())
                .link(productEntity.getLink())
                .datePurchased(convertDateToDefaultFormat(productEntity.getDatePurchased()))
                .store(productEntity.getStore())
                .build();
    }

    public List<Product> addMultipleProducts(Products products) {
        List<Product> productList = products.getProducts();
        return productList.stream()
                .map(this::addProduct)
                .collect(Collectors.toList());
    }

    private ProductEntity convertProductToEntity(Product product) {
        return ProductEntity.builder()
                .name(product.getName())
                .price(product.getPrice())
                .category(product.getCategory())
                .subCategory(product.getSubCategory())
                .link(product.getLink())
                .datePurchased(convertStrToLocalDateTime(product.getDatePurchased()))
                .store(product.getStore())
                .build();
    }
}
