package com.joliest.portfolios.groceryapi.testHelper;


import com.joliest.portfolios.groceryapi.domain.entity.GroceryEntity;
import com.joliest.portfolios.groceryapi.domain.entity.StoreEntity;
import com.joliest.portfolios.groceryapi.domain.repository.GroceryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class GroceryTestHelper {
    public static final String MOCK_GROCERY_NAME = "sample grocery";
    public static final String MOCK_GROCERY_DESC = "sample desc";
    @Autowired
    private GroceryRepository groceryRepository;
    @Autowired
    private StoreTestHelper storeTestHelper;
    public GroceryEntity setupGrocery() {
        StoreEntity storeEntity = storeTestHelper.setupStore();
        GroceryEntity groceryEntityToSave = GroceryEntity.builder()
                .name(MOCK_GROCERY_NAME)
                .description(MOCK_GROCERY_DESC)
                .store(storeEntity)
                .list(Collections.emptyList())
                .build();
        return groceryRepository.save(groceryEntityToSave);
    }
}
