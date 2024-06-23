package com.joliest.portfolios.groceryapi.service;

import com.joliest.portfolios.groceryapi.domain.entity.GroceryEntity;

import com.joliest.portfolios.groceryapi.domain.repository.GroceryRepository;

import com.joliest.portfolios.groceryapi.model.Grocery;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GroceryServiceTest {
    static Integer ID_SAMPLE = 1;
    static String NAME_SAMPLE = "New Grocery";
    static String DESCRIPTION_SAMPLE = "Description";
    @Mock
    private GroceryRepository groceryRepository;
    @InjectMocks
    private GroceryService groceryService;
    @Test
    @DisplayName("When add grocery is called," +
            "Then it saves and return the new grocery")
    void addProducts() {
        // given
        Grocery requestBody = Grocery.builder()
                .name(NAME_SAMPLE)
                .description(DESCRIPTION_SAMPLE)
                .build();

        // when
        GroceryEntity groceryToSave = GroceryEntity.builder()
                .name(NAME_SAMPLE)
                .description(DESCRIPTION_SAMPLE)
                .build();
        GroceryEntity savedGrocery = GroceryEntity.builder()
                .id(ID_SAMPLE)
                .name(NAME_SAMPLE)
                .description(DESCRIPTION_SAMPLE)
                .build();
        when(groceryRepository.save(groceryToSave))
                .thenReturn(savedGrocery);
        Grocery actual = groceryService.addGrocery(requestBody);

        // then
        Grocery expectedGrocery = Grocery.builder()
                .id(ID_SAMPLE)
                .name(NAME_SAMPLE)
                .description(DESCRIPTION_SAMPLE)
                .build();
        assertEquals(expectedGrocery, actual);
    }
}