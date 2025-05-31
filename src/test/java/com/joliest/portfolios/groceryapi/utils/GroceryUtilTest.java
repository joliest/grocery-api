package com.joliest.portfolios.groceryapi.utils;

import com.joliest.portfolios.groceryapi.domain.entity.CategoryEntity;
import com.joliest.portfolios.groceryapi.domain.entity.GroceryEntity;
import com.joliest.portfolios.groceryapi.domain.entity.GroceryItemEntity;
import com.joliest.portfolios.groceryapi.domain.entity.ProductEntity;
import com.joliest.portfolios.groceryapi.domain.entity.StoreEntity;
import com.joliest.portfolios.groceryapi.domain.entity.SubcategoryEntity;
import com.joliest.portfolios.groceryapi.model.Grocery;
import com.joliest.portfolios.groceryapi.model.GroceryItem;
import com.joliest.portfolios.groceryapi.model.Product;
import com.joliest.portfolios.groceryapi.model.Store;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GroceryUtilTest {

    @Test
    @DisplayName("Given groceryEntity is provided, " +
            "When convertEntityToGrocery is called, " +
            "Then it returns a converted grocery model")
    void convertEntityToGrocery() {
        // given
        GroceryEntity groceryEntity = GroceryEntity.builder()
                .id(1)
                .name("Name")
                .description("Description")
                .list(Collections.singletonList(GroceryItemEntity.builder()
                        .id(3)
                        .product(ProductEntity.builder()
                                .category(CategoryEntity.builder()
                                        .name("Category")
                                        .build())
                                .subcategory(SubcategoryEntity.builder()
                                        .name("Subcategory")
                                        .build())
                                .build())
                                .store(StoreEntity.builder()
                                        .id(4)
                                        .name("Store name")
                                        .description("Store description")
                                        .build())
                        .build()))
                .build();

        // then
        Grocery expected = Grocery.builder()
                .id(1)
                .name("Name")
                .description("Description")
                .list(Collections.singletonList(GroceryItem.builder()
                        .id(3)
                        .product(Product.builder()
                                .category("Category")
                                .subcategory("Subcategory")
                                .build())
                        .store(Store.builder()
                                .id(4)
                                .name("Store name")
                                .description("Store description")
                                .build())
                        .build()))
                .build();
        Grocery actual = GroceryUtil.convertEntityToGrocery(groceryEntity);
        assertEquals(actual, expected);
    }

    @Test
    @DisplayName("Given groceryEntity is provided, " +
            "And list is empty, " +
            "When convertEntityToGrocery is called, " +
            "Then it returns a converted grocery model")
    void convertEntityToGrocery_groceryListEmpty() {
        // given
        GroceryEntity groceryEntity = GroceryEntity.builder()
                .id(1)
                .name("Name")
                .description("Description")
                .list(Collections.emptyList())
                .build();

        // then
        Grocery expected = Grocery.builder()
                .id(1)
                .name("Name")
                .description("Description")
                .list(Collections.emptyList())
                .build();
        Grocery actual = GroceryUtil.convertEntityToGrocery(groceryEntity);
        assertEquals(actual, expected);
    }

}