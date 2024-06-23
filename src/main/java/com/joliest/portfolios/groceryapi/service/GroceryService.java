package com.joliest.portfolios.groceryapi.service;

import com.joliest.portfolios.groceryapi.domain.entity.GroceryEntity;
import com.joliest.portfolios.groceryapi.domain.repository.GroceryRepository;
import com.joliest.portfolios.groceryapi.model.Grocery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public List<Grocery> getGroceries() {
        List<GroceryEntity> fetchedGroceries = groceryRepository.findAll();
        return fetchedGroceries.stream()
                .map(groceryEntity -> Grocery.builder()
                        .id(groceryEntity.getId())
                        .name(groceryEntity.getName())
                        .description(groceryEntity.getDescription())
                        .build())
                .toList();
    }
}
