package com.joliest.portfolios.groceryapi.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class Product {
    private Integer id;
    private String name;
    private String category;
    private String subcategory;
    private List<PurchaseHistory> purchaseHistoryList;
}
