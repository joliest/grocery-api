package com.joliest.portfolios.groceryapi.service;

import com.joliest.portfolios.groceryapi.domain.entity.StoreEntity;
import com.joliest.portfolios.groceryapi.domain.repository.StoreRepository;
import com.joliest.portfolios.groceryapi.model.Store;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StoreService {
    private final StoreRepository storeRepository;
    public List<Store> getStores() {
        List<StoreEntity> storeEntities = storeRepository.findAll();
        return storeEntities.stream()
                .map(storeEntity -> Store.builder()
                        .id(storeEntity.getId())
                        .name(storeEntity.getName())
                        .description(storeEntity.getDescription())
                        .build())
                .collect(Collectors.toList());
    }

    public List<Store> addMultipleStores(List<Store> stores) {
        List<StoreEntity> storeEntities = stores.stream()
                .map(store -> StoreEntity.builder()
                        .name(store.getName())
                        .description(store.getDescription())
                        .build())
                .collect(Collectors.toList());
        List<StoreEntity> savedStores = storeRepository.saveAll(storeEntities);
        return savedStores.stream().map(storeEntity -> Store.builder()
                .id(storeEntity.getId())
                    .name(storeEntity.getName())
                    .description(storeEntity.getDescription())
                    .build())
                .collect(Collectors.toList());
    }
}
