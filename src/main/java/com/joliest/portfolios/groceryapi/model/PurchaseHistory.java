package com.joliest.portfolios.groceryapi.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PurchaseHistory {
    private Integer id;
    private Long price;
    private String datePurchased;
    private String link;
    private String store;
}
