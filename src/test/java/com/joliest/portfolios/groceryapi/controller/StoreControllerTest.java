package com.joliest.portfolios.groceryapi.controller;

import com.joliest.portfolios.groceryapi.model.Store;
import com.joliest.portfolios.groceryapi.service.StoreService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.annotation.Description;

import java.util.List;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StoreControllerTest {
    @Mock
    private StoreService storeService;
    @InjectMocks
    private StoreController storeController;

    @Test
    @Description("when get store is called, it should return list of stores")
    public void getStore() {
        // when
        List<Store> expected = getMockStores();
        when(storeService.getStores()).thenReturn(expected);
        List<Store> actual = storeController.getStores();

        // then
        assertEquals(expected, actual);
    }

    @Test
    @Description("when post store is called, it should save the given store")
    public void postStore() {
        // given
        List<Store> requestBody = asList(Store.builder()
                .name("Store 1")
                .build());
        List<Store> responseBody = asList(Store.builder()
                .name("Store 1")
                .build());
        // when
        when(storeService.addMultipleStores(requestBody)).thenReturn(responseBody);
        List<Store> actual = storeController.addMultipleStores(requestBody);

        // then
        List<Store> expected = asList(Store.builder()
                .name("Store 1")
                .build());
        assertEquals(expected, actual);
    }

    private List<Store> getMockStores() {
        return asList(
                Store.builder()
                        .id(1)
                        .name("Product 1")
                        .build(),
                Store.builder()
                        .id(2)
                        .name("Product 2")
                        .build());
    }
}