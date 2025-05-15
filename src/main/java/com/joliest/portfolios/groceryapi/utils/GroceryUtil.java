package com.joliest.portfolios.groceryapi.utils;

import com.joliest.portfolios.groceryapi.domain.entity.GroceryEntity;
import com.joliest.portfolios.groceryapi.domain.entity.GroceryItemEntity;
import com.joliest.portfolios.groceryapi.model.Grocery;
import com.joliest.portfolios.groceryapi.model.GroceryItem;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.util.CollectionUtils.isEmpty;

public class GroceryUtil {

    public static Grocery convertEntityToGrocery(GroceryEntity groceryEntity) {
        List<GroceryItem> list = new ArrayList<>();
        if (!isEmpty(groceryEntity.getList())) {
            list = groceryEntity.getList().stream()
                    .map(GroceryUtil::convertEntityToGroceryItem)
                    .toList();
        }
        return Grocery.builder()
                .id(groceryEntity.getId())
                .name(groceryEntity.getName())
                .description(groceryEntity.getDescription())
                .list(list)
                .build();
    }

    public static GroceryItem convertEntityToGroceryItem(GroceryItemEntity groceryItemEntity) {
        return GroceryItem.builder()
                .id(groceryItemEntity.getId())
                .product(ProductUtil.convertEntityToProduct(groceryItemEntity.getProduct(), false))
                .quantity(groceryItemEntity.getQuantity())
                .actualPrice(groceryItemEntity.getActualPrice())
                .estimatedPrice(groceryItemEntity.getEstimatedPrice())
                .notes(groceryItemEntity.getNotes())
                .build();
    }
}
