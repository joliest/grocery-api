package com.joliest.portfolios.groceryapi.utils;

import com.joliest.portfolios.groceryapi.domain.entity.ProductEntity;
import com.joliest.portfolios.groceryapi.model.Product;
import com.joliest.portfolios.groceryapi.model.ProductImport;
import com.joliest.portfolios.groceryapi.model.PurchaseHistory;

import java.util.Collections;
import java.util.List;

import static com.joliest.portfolios.groceryapi.utils.DateUtil.convertDateToDefaultFormat;

public class ProductUtil {
    public static Product convertEntityToProduct(ProductEntity productEntity, boolean includeHistory) {
        List<PurchaseHistory> purchaseHistoryList = null;
        if (includeHistory) {
            purchaseHistoryList = productEntity.getHistories()
                    .stream()
                    .map(purchaseHistoryEntity -> PurchaseHistory.builder()
                            .id(purchaseHistoryEntity.getId())
                            .link(purchaseHistoryEntity.getLink())
                            .store(purchaseHistoryEntity.getStore().getName())
                            .price(purchaseHistoryEntity.getPrice())
                            .datePurchased(convertDateToDefaultFormat(purchaseHistoryEntity.getDatePurchased()))
                            .build())
                    .toList();
        }

        return Product.builder()
                .id(productEntity.getId())
                .name(productEntity.getName())
                .category(productEntity.getCategory().getName())
                .subcategory(productEntity.getSubcategory().getName())
                .purchaseHistoryList(purchaseHistoryList)
                .build();

    }

    public static Product convertEntityToProduct(ProductEntity productEntity) {
        return convertEntityToProduct(productEntity, true);
    }
}
