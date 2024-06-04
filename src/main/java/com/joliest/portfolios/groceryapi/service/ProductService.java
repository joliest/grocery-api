package com.joliest.portfolios.groceryapi.service;

import com.joliest.portfolios.groceryapi.domain.entity.CategoryEntity;
import com.joliest.portfolios.groceryapi.domain.entity.ProductEntity;
import com.joliest.portfolios.groceryapi.domain.entity.StoreEntity;
import com.joliest.portfolios.groceryapi.domain.entity.SubcategoryEntity;
import com.joliest.portfolios.groceryapi.domain.repository.CategoryRepository;
import com.joliest.portfolios.groceryapi.domain.repository.ProductRepository;
import com.joliest.portfolios.groceryapi.domain.repository.StoreRepository;
import com.joliest.portfolios.groceryapi.domain.repository.SubcategoryRepository;
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
    private final CategoryRepository categoryRepository;
    private final SubcategoryRepository subcategoryRepository;

    public List<Product> getProducts() {
        return productRepository.findAll().stream()
                .map(productEntity ->
                        Product.builder()
                                .id(productEntity.getId())
                                .name(productEntity.getName())
                                .price(productEntity.getPrice())
                                .store(productEntity.getStore().getName())
                                .category(productEntity.getCategory().getName())
                                .subcategory(productEntity.getSubcategory().getName())
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
                .category(productEntity.getCategory().getName())
                .subcategory(productEntity.getSubcategory().getName())
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
        StoreEntity storeEntity = getProductStoreEntity(product.getStore());
        CategoryEntity categoryEntity = getProductCategoryEntity(product.getCategory());
        SubcategoryEntity subcategoryEntity = getProductSubcategoryAndAssignToCategory(product.getSubcategory(), categoryEntity);
        return ProductEntity.builder()
                .name(product.getName())
                .price(product.getPrice())
                .category(categoryEntity)
                .subcategory(subcategoryEntity)
                .link(product.getLink())
                .datePurchased(convertStrToLocalDateTime(product.getDatePurchased()))
                .store(storeEntity)
                .store(storeEntity)
                .build();
    }

    private SubcategoryEntity getProductSubcategoryAndAssignToCategory(String subcategory, CategoryEntity categoryEntity) {
        Optional<SubcategoryEntity> foundEntity = subcategoryRepository.findByNameAndCategory(subcategory, categoryEntity);
        if (foundEntity.isEmpty()) {
            SubcategoryEntity newSubcategory = SubcategoryEntity.builder()
                    .category(categoryEntity)
                    .name(subcategory)
                    .build();
            return subcategoryRepository.save(newSubcategory);
        }
        return foundEntity.get();
    }

    private CategoryEntity getProductCategoryEntity(String category) {
        Optional<CategoryEntity> foundEntity = categoryRepository.findByName(category);
        if (foundEntity.isEmpty()) {
            return createNewCategory(category);
        }
        return foundEntity.get();
    }

    private CategoryEntity createNewCategory(String category) {
        return categoryRepository.save(CategoryEntity.builder()
                .name(category)
                .build());
    }

    private StoreEntity getProductStoreEntity(String productStore) {
        Optional<StoreEntity> foundEntity = storeRepository.findByName(productStore);
        if (foundEntity.isEmpty()) {
            return createNewStore(productStore);
        }
        return foundEntity.get();
    }

    private StoreEntity createNewStore(String productStore) {
        return storeRepository.save(StoreEntity.builder()
                .name(productStore)
                .build());
    }
}
