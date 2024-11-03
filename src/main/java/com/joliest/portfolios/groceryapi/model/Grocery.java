package com.joliest.portfolios.groceryapi.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Grocery {
    private Integer id;
    private String name;
    private String description;
    private Store store;
    private List<GroceryItem> list = new ArrayList<>();
}