package com.joliest.portfolios.groceryapi.utils;

import com.joliest.portfolios.groceryapi.domain.entity.StoreEntity;
import com.joliest.portfolios.groceryapi.model.Store;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StoreUtilTest {

    @Test
    @DisplayName("Given storeEntity is provided," +
            "When convertEntityStore is called, " +
            "Then it returns a converted store")
    void convertEntityStore() {
        // given
        StoreEntity givenStoreEntity = StoreEntity.builder().build();

        // then
        Store expected = Store.builder().build();
        Store actual = StoreUtil.convertEntityToStore(givenStoreEntity);

        assertEquals(expected, actual);
    }
}