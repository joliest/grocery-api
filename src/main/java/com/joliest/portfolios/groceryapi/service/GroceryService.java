package com.joliest.portfolios.groceryapi.service;

import com.joliest.portfolios.groceryapi.domain.entity.GroceryEntity;
import com.joliest.portfolios.groceryapi.domain.repository.GroceryRepository;
import com.joliest.portfolios.groceryapi.model.Grocery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GroceryService {
    private final GroceryRepository groceryRepository;
    public Grocery addGrocery(Grocery grocery) {
        GroceryEntity groceryEntity = GroceryEntity.builder()
                .name(grocery.getName())
                .description(grocery.getDescription())
                .build();
        GroceryEntity savedGrocery = groceryRepository.save(groceryEntity);
        return Grocery.builder()
                .id(savedGrocery.getId())
                .description(savedGrocery.getDescription())
                .name(savedGrocery.getName())
                .build();
    }
}
