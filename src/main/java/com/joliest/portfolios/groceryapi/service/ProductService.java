package com.joliest.portfolios.groceryapi.service;

import com.joliest.portfolios.groceryapi.domain.entity.CategoryEntity;
import com.joliest.portfolios.groceryapi.domain.entity.ProductEntity;
import com.joliest.portfolios.groceryapi.domain.entity.PurchaseHistoryEntity;
import com.joliest.portfolios.groceryapi.domain.entity.StoreEntity;
import com.joliest.portfolios.groceryapi.domain.entity.SubcategoryEntity;
import com.joliest.portfolios.groceryapi.domain.repository.CategoryRepository;
import com.joliest.portfolios.groceryapi.domain.repository.PurchaseHistoryRepository;
import com.joliest.portfolios.groceryapi.domain.repository.ProductRepository;
import com.joliest.portfolios.groceryapi.domain.repository.StoreRepository;
import com.joliest.portfolios.groceryapi.domain.repository.SubcategoryRepository;
import com.joliest.portfolios.groceryapi.model.Product;
import com.joliest.portfolios.groceryapi.model.Products;
import com.joliest.portfolios.groceryapi.model.PurchaseHistory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    private final PurchaseHistoryRepository purchaseHistoryRepository;

    public List<Product> getProducts() {
        return productRepository.findAll().stream()
                .map(productEntity -> {
                    List<PurchaseHistory> purchaseHistoryList = productEntity.getHistories()
                            .stream()
                            .map(purchaseHistoryEntity -> PurchaseHistory.builder()
                                    .store(productEntity.getStore().getName())
                                    .id(purchaseHistoryEntity.getId())
                                    .link(purchaseHistoryEntity.getLink())
                                    .price(purchaseHistoryEntity.getPrice())
                                    .datePurchased(convertDateToDefaultFormat(purchaseHistoryEntity.getDatePurchased()))
                                    .build())
                            .toList();

                    return Product.builder()
                            .id(productEntity.getId())
                            .name(productEntity.getName())
                            .price(productEntity.getPrice())
                            .store(productEntity.getStore().getName())
                            .category(productEntity.getCategory().getName())
                            .subcategory(productEntity.getSubcategory().getName())
                            .link(productEntity.getLink())
                            .datePurchased(convertDateToDefaultFormat(productEntity.getDatePurchased()))
                            .purchaseHistoryList(purchaseHistoryList)
                            .build();
                }).collect(Collectors.toList());
    }

    public Product addProduct(Product product) {
        ProductEntity productEntity = convertProductToEntity(product);
        PurchaseHistoryEntity productHistoryEntity = PurchaseHistoryEntity.builder()
                .product(productEntity)
                .store(productEntity.getStore())
                .price(product.getPrice())
                .datePurchased(convertStrToLocalDateTime(product.getDatePurchased()))
                .link(product.getLink())
                .build();
        purchaseHistoryRepository.save(productHistoryEntity);

        return Product.builder()
                .id(productEntity.getId())
                .name(productEntity.getName())
                .category(productEntity.getCategory().getName())
                .subcategory(productEntity.getSubcategory().getName())
                .datePurchased(convertDateToDefaultFormat(productHistoryEntity.getDatePurchased()))
                .price(productHistoryEntity.getPrice())
                .link(productHistoryEntity.getLink())
                .store(productHistoryEntity.getStore().getName())
                .build();
    }

    @Transactional
    public List<Product> addMultipleProducts(Products products) {
        List<Product> productList = products.getProducts();
        return productList.stream()
                .map(this::addProduct)
                .collect(Collectors.toList());
    }

    private ProductEntity convertProductToEntity(Product product) {
        Optional<ProductEntity> foundProduct = findProduct(product);
        StoreEntity storeEntity = getProductStoreEntity(product.getStore());

        if (foundProduct.isEmpty()) {
            CategoryEntity categoryEntity = getProductCategoryEntity(product.getCategory());
            SubcategoryEntity subcategoryEntity = getProductSubcategoryAndAssignToCategory(product.getSubcategory(), categoryEntity);
            ProductEntity newProduct =  ProductEntity.builder()
                    .name(product.getName())
                    .price(product.getPrice())
                    .category(categoryEntity)
                    .subcategory(subcategoryEntity)
                    .link(product.getLink())
                    .datePurchased(convertStrToLocalDateTime(product.getDatePurchased()))
                    .store(storeEntity)
                    .build();
            return productRepository.save(newProduct);
        }
        return foundProduct.get();
    }

    private Optional<ProductEntity> findProduct(Product product) {
        return productRepository.findFirstByNameAndCategoryNameAndSubcategoryName(
                product.getName(),
                product.getCategory(),
                product.getSubcategory());
    }

    private StoreEntity getProductStoreEntity(String productStore) {
        Optional<StoreEntity> foundEntity = storeRepository.findByName(productStore);
        return foundEntity.orElseGet(() -> createNewStore(productStore));
    }

    private SubcategoryEntity getProductSubcategoryAndAssignToCategory(String subcategory, CategoryEntity categoryEntity) {
        Optional<SubcategoryEntity> foundEntity = subcategoryRepository.findByNameAndCategory(subcategory, categoryEntity);
        return foundEntity.orElseGet(() -> createNewSubcategory(subcategory, categoryEntity));
    }

    private CategoryEntity getProductCategoryEntity(String category) {
        Optional<CategoryEntity> foundEntity = categoryRepository.findByName(category);
        return foundEntity.orElseGet(() -> createNewCategory(category));
    }

    private CategoryEntity createNewCategory(String category) {
        return categoryRepository.save(CategoryEntity.builder()
                .name(category)
                .build());
    }

    private SubcategoryEntity createNewSubcategory(String subcategory, CategoryEntity categoryEntity) {
        return subcategoryRepository.save(SubcategoryEntity.builder()
                .category(categoryEntity)
                .name(subcategory)
                .build());
    }

    private StoreEntity createNewStore(String productStore) {
        return storeRepository.save(StoreEntity.builder()
                .name(productStore)
                .build());
    }
}
