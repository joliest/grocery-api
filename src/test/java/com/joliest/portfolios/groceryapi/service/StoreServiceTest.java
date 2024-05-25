package com.joliest.portfolios.groceryapi.service;

import com.joliest.portfolios.groceryapi.domain.entity.StoreEntity;
import com.joliest.portfolios.groceryapi.domain.repository.StoreRepository;
import com.joliest.portfolios.groceryapi.model.Store;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StoreServiceTest {
    @Mock
    private StoreRepository storeRepository;
    @InjectMocks
    private StoreService storeService;

    @Test
    void getStores() {
        // when
        List<StoreEntity> storesFromDb = asList(StoreEntity.builder()
                .id(1)
                .name("Store 1")
                .description("Desc 1")
                .build());
        when(storeRepository.findAll()).thenReturn(storesFromDb);

        //then
        List<Store> expected = asList(Store.builder()
                .id(1)
                .name("Store 1")
                .description("Desc 1")
                .build());
        List<Store> actual = storeService.getStores();
        assertEquals(expected, actual);
    }

    @Test
    void addMultipleStores() {
        // given
        List<Store> requestBody = asList(Store.builder()
                .id(1)
                .name("Store 1")
                .description("Desc 1")
                .build());
        // when
        List<StoreEntity> savedStore = asList(StoreEntity.builder()
                .id(1)
                .name("Store 1")
                .description("Desc 1")
                .build());
        when(storeRepository.saveAll(anyList())).thenReturn(savedStore);

        //then
        List<Store> expected = asList(Store.builder()
                .id(1)
                .name("Store 1")
                .description("Desc 1")
                .build());
        List<Store> actual = storeService.addMultipleStores(requestBody);
        assertEquals(expected, actual);
    }

}