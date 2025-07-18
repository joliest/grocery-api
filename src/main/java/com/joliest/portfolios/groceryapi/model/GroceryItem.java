package com.joliest.portfolios.groceryapi.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GroceryItem {
    private Integer id;
    private Float quantity;
    private String notes;
    private Long actualPrice;
    private Long estimatedPrice;

    private Product product;
    private Store store;
}
