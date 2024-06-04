package com.joliest.portfolios.groceryapi.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class Category {
    private Integer id;
    private String name;
    private String description;
    private List<String> subcategories;
}
