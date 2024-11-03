package com.joliest.portfolios.groceryapi.controller;

import com.joliest.portfolios.groceryapi.model.Grocery;
import com.joliest.portfolios.groceryapi.model.GroceryRequestModel;
import com.joliest.portfolios.groceryapi.service.GroceryService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.annotation.Description;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GroceryControllerTest {
    static Integer ID_SAMPLE = 1;
    static String NAME_SAMPLE = "New Grocery";
    static String DESCRIPTION_SAMPLE = "Description";
    @Mock
    private GroceryService groceryService;
    @InjectMocks
    private GroceryController groceryController;

    @Test
    @Description("When add grocery is called with grocery" +
            "Then it should return the new grocery")
    public void addGrocery() {
        // given
        GroceryRequestModel requestBody = GroceryRequestModel.builder()
                .name(NAME_SAMPLE)
                .description(DESCRIPTION_SAMPLE)
                .build();

        // when
        Grocery newGrocery = Grocery.builder()
                .id(1)
                .name(NAME_SAMPLE)
                .description(DESCRIPTION_SAMPLE)
                .build();
        when(groceryService.addGrocery(requestBody))
                .thenReturn(newGrocery);
        Grocery actual = groceryController.addGrocery(requestBody);

        // then
        Grocery expectedGrocery = Grocery.builder()
                .id(1)
                .name(NAME_SAMPLE)
                .description(DESCRIPTION_SAMPLE)
                .build();
        assertEquals(expectedGrocery, actual);
    }

    @Test
    @Description("When get groceries is called" +
            "Then it should return the the list of saved groceries")
    public void getGroceries() {
        // when
        List<Grocery> fetchedGroceries = Collections.singletonList(Grocery.builder()
                .id(ID_SAMPLE)
                .name(NAME_SAMPLE)
                .name(DESCRIPTION_SAMPLE)
                .build());
        when(groceryService.getGroceries())
                .thenReturn(fetchedGroceries);
        List<Grocery> actual = groceryService.getGroceries();

        // then
        List<Grocery>  expectedGroceries = Collections.singletonList(Grocery.builder()
                .id(ID_SAMPLE)
                .name(NAME_SAMPLE)
                .name(DESCRIPTION_SAMPLE)
                .build());
        assertEquals(expectedGroceries, actual);
    }
}