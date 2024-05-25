package com.joliest.portfolios.groceryapi.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Store {
    private Integer id;
    private String name;
    private String description;
}
