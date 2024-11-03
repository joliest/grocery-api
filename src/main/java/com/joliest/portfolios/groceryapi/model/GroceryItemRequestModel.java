package com.joliest.portfolios.groceryapi.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GroceryItemRequestModel {
    private Integer id;

    @Positive
    private Float quantity;
    private String notes;
    private Long actualPrice;
    private Long estimatedPrice;

    @NotNull
    private Integer productId;
}