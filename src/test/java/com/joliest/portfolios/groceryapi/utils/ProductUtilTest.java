package com.joliest.portfolios.groceryapi.utils;

import com.joliest.portfolios.groceryapi.domain.entity.CategoryEntity;
import com.joliest.portfolios.groceryapi.domain.entity.ProductEntity;
import com.joliest.portfolios.groceryapi.domain.entity.PurchaseHistoryEntity;
import com.joliest.portfolios.groceryapi.domain.entity.StoreEntity;
import com.joliest.portfolios.groceryapi.domain.entity.SubcategoryEntity;
import com.joliest.portfolios.groceryapi.model.Product;
import com.joliest.portfolios.groceryapi.model.PurchaseHistory;
import com.joliest.portfolios.groceryapi.model.Store;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProductUtilTest {
    @Test
    @DisplayName("Given productEntity is provided, " +
            "When convertEntityToProduct is called, " +
            "Then it returns a converted product model")
    void convertEntityToProduct() {
        // given
        LocalDate localDate = LocalDate.parse("2023-05-13");
        LocalTime localTime = LocalTime.parse("07:52:41.197653");
        LocalDateTime localDateTime = LocalDateTime.of(localDate, localTime);
        List<PurchaseHistoryEntity> purchaseHistoryEntities = Collections.singletonList(PurchaseHistoryEntity.builder()
                .id(111)
                .store(StoreEntity.builder().build())
                .datePurchased(localDateTime)
                .build());
        ProductEntity productEntity = ProductEntity.builder()
                .id(222)
                .category(CategoryEntity.builder()
                        .name("Category")
                        .build())
                .subcategory(SubcategoryEntity.builder()
                        .name("Subcategory")
                        .build())
                .histories(purchaseHistoryEntities)
                .build();

        // then
        Product expected = Product.builder()
                .id(222)
                .category("Category")
                .subcategory("Subcategory")
                .purchaseHistoryList(Collections.singletonList(PurchaseHistory.builder()
                        .id(111)
                        .datePurchased("05-13-2023")
                        .build()))
                .build();
        Product actual = ProductUtil.convertEntityToProduct(productEntity);
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Given productEntity is provided," +
            "And includeHistory is false " +
            "When convertEntityToProduct is called, " +
            "Then it returns a converted product model without purchase history")
    void convertEntityToProduct_dontIncludeHistory() {
        // given
        LocalDate localDate = LocalDate.parse("2023-05-13");
        LocalTime localTime = LocalTime.parse("07:52:41.197653");
        LocalDateTime localDateTime = LocalDateTime.of(localDate, localTime);
        List<PurchaseHistoryEntity> purchaseHistoryEntities = Collections.singletonList(PurchaseHistoryEntity.builder()
                .id(111)
                .store(StoreEntity.builder().build())
                .datePurchased(localDateTime)
                .build());
        ProductEntity productEntity = ProductEntity.builder()
                .id(222)
                .category(CategoryEntity.builder()
                        .name("Category")
                        .build())
                .subcategory(SubcategoryEntity.builder()
                        .name("Subcategory")
                        .build())
                .histories(purchaseHistoryEntities)
                .build();
        boolean includeHistory = false;

        // then
        Product expected = Product.builder()
                .id(222)
                .category("Category")
                .subcategory("Subcategory")
                .purchaseHistoryList(null)
                .build();
        Product actual = ProductUtil.convertEntityToProduct(productEntity, includeHistory);
        assertEquals(expected, actual);
    }
}