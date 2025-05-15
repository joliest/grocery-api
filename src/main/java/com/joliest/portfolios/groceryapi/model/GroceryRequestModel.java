package com.joliest.portfolios.groceryapi.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GroceryRequestModel {
    private Integer id;
    private String name;
    private String description;
}