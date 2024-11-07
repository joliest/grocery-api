package com.joliest.portfolios.groceryapi.testHelper;

import com.joliest.portfolios.groceryapi.domain.entity.StoreEntity;
import com.joliest.portfolios.groceryapi.domain.repository.StoreRepository;
import com.joliest.portfolios.groceryapi.model.Store;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.Arrays.asList;

@Service
public class StoreTestHelper {
    public static final String MOCK_STORE_NAME = "sample store";
    public static final String MOCK_STORE_DESC = "sample desc";
    @Autowired
    private StoreRepository storeRepository;
    public StoreEntity setupStore() {
        StoreEntity storeEntityToSave = StoreEntity.builder()
                .name(MOCK_STORE_NAME)
                .description(MOCK_STORE_DESC)
                .build();
        return storeRepository.save(storeEntityToSave);
    }

    public List<Store> createRequestBody() {
        Store store1 = Store.builder()
                .name("Store 1")
                .description("Desc 1")
                .build();
        Store store2 = Store.builder()
                .name("Store 2")
                .description("Desc 2")
                .build();
        return asList(store1, store2);
    }

    public void cleanupStore() {
        storeRepository.deleteAll();
    }
}
