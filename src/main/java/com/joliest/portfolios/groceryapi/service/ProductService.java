package com.joliest.portfolios.groceryapi.service;

import com.joliest.portfolios.groceryapi.domain.repository.ProductRepository;
import com.joliest.portfolios.groceryapi.model.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.joliest.portfolios.groceryapi.utils.DateUtil.convertDateToDefaultFormat;

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
}
