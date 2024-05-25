package com.joliest.portfolios.groceryapi.service;

import com.joliest.portfolios.groceryapi.domain.entity.ProductEntity;
import com.joliest.portfolios.groceryapi.domain.entity.StoreEntity;
import com.joliest.portfolios.groceryapi.domain.repository.ProductRepository;
import com.joliest.portfolios.groceryapi.domain.repository.StoreRepository;
import com.joliest.portfolios.groceryapi.model.Product;
import com.joliest.portfolios.groceryapi.model.Products;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.joliest.portfolios.groceryapi.utils.DateUtil.convertDateToDefaultFormat;
import static com.joliest.portfolios.groceryapi.utils.DateUtil.convertStrToLocalDateTime;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final StoreRepository storeRepository;

    public List<Product> getProducts() {
        return productRepository.findAll().stream()
                .map(productEntity ->
                        Product.builder()
                                .id(productEntity.getId())
                                .name(productEntity.getName())
                                .price(productEntity.getPrice())
                                .store(productEntity.getStore().getName())
                                .category(productEntity.getCategory())
                                .subcategory(productEntity.getSubcategory())
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
                .subcategory(productEntity.getSubcategory())
                .link(productEntity.getLink())
                .datePurchased(convertDateToDefaultFormat(productEntity.getDatePurchased()))
                .store(productEntity.getStore().getName())
                .build();
    }

    public List<Product> addMultipleProducts(Products products) {
        List<Product> productList = products.getProducts();
        return productList.stream()
                .map(this::addProduct)
                .collect(Collectors.toList());
    }

    private ProductEntity convertProductToEntity(Product product) {
        String productStore = product.getStore();
        Optional<StoreEntity> foundEntity = storeRepository
                .findByName(product.getStore());
        StoreEntity storeEntity;
        if (foundEntity.isEmpty()) {
            storeEntity = storeRepository.save(StoreEntity.builder()
                    .name(productStore)
                    .build());
        } else {
            storeEntity = foundEntity.get();
        }
        return ProductEntity.builder()
                .name(product.getName())
                .price(product.getPrice())
                .category(product.getCategory())
                .subcategory(product.getSubcategory())
                .link(product.getLink())
                .datePurchased(convertStrToLocalDateTime(product.getDatePurchased()))
                .store(storeEntity)
                .build();
    }
}
