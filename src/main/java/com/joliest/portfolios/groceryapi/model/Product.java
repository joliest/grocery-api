package com.joliest.portfolios.groceryapi.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class Product {
    private Integer id;
    private String name;
    private Long price;
    // make an object
    private String store;
    private String category;
    private String subcategory;
    private String link;
    private String datePurchased;
    private List<PurchaseHistory> purchaseHistoryList;
}
