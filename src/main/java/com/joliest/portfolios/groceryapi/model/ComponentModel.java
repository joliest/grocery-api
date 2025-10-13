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
public class ComponentModel {
    private Integer id;
    private String name;
    private String description;
    private List<Item> list;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Item {
        private Integer id;
        private Product product;
        private Integer quantity = 0;
        private Boolean included;
    }
}