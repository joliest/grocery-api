package com.joliest.portfolios.groceryapi.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Product {
    private Integer id;
    private String name;
    private Long price;
    // make an object
    private String store;
    private String category;
    private String subCategory;
    private String link;
    private String datePurchased;
}
