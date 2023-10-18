package com.joliest.portfolios.groceryapi.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class Products {
    private List<Product> products;
}
