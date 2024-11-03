package com.joliest.portfolios.groceryapi.model;

import com.fasterxml.jackson.annotation.JsonInclude;
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

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<PurchaseHistory> purchaseHistoryList;
}
