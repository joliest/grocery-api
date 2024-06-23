package com.joliest.portfolios.groceryapi.controller;

import com.joliest.portfolios.groceryapi.model.Grocery;
import com.joliest.portfolios.groceryapi.service.GroceryService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.annotation.Description;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GroceryControllerTest {
    @Mock
    private GroceryService groceryService;
    @InjectMocks
    private GroceryController groceryController;

    @Test
    @Description("When add grocery is called with grocery" +
            "Then it should return the new grocery")
    public void addGrocery() {
        // given
        Grocery requestBody = Grocery.builder()
                .name("Grocery 1")
                .description("Grocery description")
                .build();

        // when
        Grocery newGrocery = Grocery.builder()
                .id(1)
                .name("Grocery 1")
                .description("Grocery description")
                .build();
        when(groceryService.addGrocery(requestBody))
                .thenReturn(newGrocery);
        Grocery actual = groceryController.addGrocery(requestBody);

        // then
        Grocery expectedGrocery = Grocery.builder()
                .id(1)
                .name("Grocery 1")
                .description("Grocery description")
                .build();
        assertEquals(expectedGrocery, actual);
    }
}