package com.joliest.portfolios.groceryapi.utils;

import com.joliest.portfolios.groceryapi.domain.entity.StoreEntity;
import com.joliest.portfolios.groceryapi.model.Store;

public class StoreUtil {
    public static Store convertEntityToStore(StoreEntity storeEntity) {
        return Store.builder()
                .id(storeEntity.getId())
                .name(storeEntity.getName())
                .description(storeEntity.getDescription())
                .build();
    }
}
