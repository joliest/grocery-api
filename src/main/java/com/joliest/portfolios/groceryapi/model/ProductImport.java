package com.joliest.portfolios.groceryapi.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductImport {
    private Integer id;
    private String name;
    private Long price;
    private String store;
    private String category;
    private String subcategory;
    private String link;
    private String datePurchased;
    private List<PurchaseHistory> purchaseHistoryList;
}
