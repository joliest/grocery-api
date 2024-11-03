package com.joliest.portfolios.groceryapi.service;

import com.joliest.portfolios.groceryapi.domain.entity.CategoryEntity;
import com.joliest.portfolios.groceryapi.domain.entity.GroceryEntity;

import com.joliest.portfolios.groceryapi.domain.entity.GroceryItemEntity;
import com.joliest.portfolios.groceryapi.domain.entity.ProductEntity;
import com.joliest.portfolios.groceryapi.domain.entity.StoreEntity;
import com.joliest.portfolios.groceryapi.domain.entity.SubcategoryEntity;
import com.joliest.portfolios.groceryapi.domain.repository.GroceryItemRepository;
import com.joliest.portfolios.groceryapi.domain.repository.GroceryRepository;

import com.joliest.portfolios.groceryapi.domain.repository.ProductRepository;
import com.joliest.portfolios.groceryapi.domain.repository.StoreRepository;
import com.joliest.portfolios.groceryapi.exception.NotFoundException;
import com.joliest.portfolios.groceryapi.model.Grocery;
import com.joliest.portfolios.groceryapi.model.GroceryItem;
import com.joliest.portfolios.groceryapi.model.GroceryItemRequestModel;
import com.joliest.portfolios.groceryapi.model.GroceryRequestModel;
import com.joliest.portfolios.groceryapi.model.Product;
import com.joliest.portfolios.groceryapi.model.Store;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GroceryServiceTest {
    static Integer MOCK_PRODUCT_ID = 1;
    static Integer MOCK_STORE_ID = 2;
    static Integer MOCK_GROCERY_ID = 3;
    static String MOCK_GROCERY_NAME = "New Grocery";
    static String MOCK_DESCRIPTION = "Description";

    @Mock
    private GroceryRepository groceryRepository;

    @Mock
    private StoreRepository storeRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private GroceryItemRepository groceryItemRepository;

    @InjectMocks
    private GroceryService groceryService;

    @Test
    @DisplayName("When add grocery is called, " +
            "Then it saves and return the new grocery")
    void addGrocery() {
        // given
        GroceryRequestModel requestBody = GroceryRequestModel.builder()
                .name(MOCK_GROCERY_NAME)
                .description(MOCK_DESCRIPTION)
                .storeId(MOCK_STORE_ID)
                .build();

        // when
        StoreEntity foundStoreEntity = StoreEntity.builder().build();
        when(storeRepository.findById(any(Integer.class)))
                .thenReturn(Optional.of(foundStoreEntity));
        when(groceryRepository.save(any(GroceryEntity.class)))
                .thenReturn(GroceryEntity.builder()
                        .store(StoreEntity.builder().build())
                        .build());

        Grocery convertedEntityToGrocery = Grocery.builder()
                .store(Store.builder().build())
                .list(emptyList())
                .build();

        // then
        Grocery actual = groceryService.addGrocery(requestBody);
        Grocery expected = convertedEntityToGrocery;
        assertEquals(actual, expected);
    }

    @Test
    @DisplayName("Given store id is not found" +
            "When add grocery is called," +
            "Then it does not save and throws error")
    void addGrocery_storeNotExist() {
        // given
        Optional<StoreEntity> foundStoreEntity = Optional.empty();
        GroceryRequestModel requestBody = GroceryRequestModel.builder()
                .name(MOCK_GROCERY_NAME)
                .description(MOCK_DESCRIPTION)
                .storeId(MOCK_STORE_ID)
                .build();

        // when
        when(storeRepository.findById(any(Integer.class)))
                .thenReturn(foundStoreEntity);

        // then
        assertThrows(NotFoundException.class, () -> groceryService.addGrocery(requestBody));
        verify(groceryRepository, never()).save(any(GroceryEntity.class));
    }

    @Test
    @DisplayName("When get groceries is called" +
            "Then it returns list of saved groceries")
    void getGroceries() {
        // when
        List<GroceryEntity> fetchedGroceries = singletonList(GroceryEntity.builder()
                .store(StoreEntity.builder().build())
                .build());
        when(groceryRepository.findAll()).thenReturn(fetchedGroceries);

        // then
        List<Grocery> expectedGroceries = singletonList(Grocery.builder()
                .store(Store.builder().build())
                .list(emptyList())
                .build());
        List<Grocery> actual = groceryService.getGroceries();
        assertEquals(expectedGroceries, actual);
    }

    @Test
    @DisplayName("Given grocery id and request body is provided" +
            "When add groceries item is called" +
            "Then it returns list of saved groceries")
    void addGroceryItem() {
        // given
        Integer groceryId = MOCK_GROCERY_ID;
        GroceryItemRequestModel requestModel = GroceryItemRequestModel.builder()
                .productId(MOCK_PRODUCT_ID)
                .build();

        // when
        when(groceryRepository.findById(MOCK_GROCERY_ID))
                .thenReturn(Optional.of(GroceryEntity.builder().build()));
        when(productRepository.findById(MOCK_PRODUCT_ID))
                .thenReturn(Optional.of(ProductEntity.builder().build()));

        GroceryItemEntity savedGroceryItemEntity = GroceryItemEntity.builder()
                .product(ProductEntity.builder()
                        .category(CategoryEntity.builder()
                                .name("Category")
                                .build())
                        .subcategory(SubcategoryEntity.builder()
                                .name("Subcategory")
                                .build())
                        .build())
                .build();
        when(groceryItemRepository.save(any(GroceryItemEntity.class)))
                .thenReturn(savedGroceryItemEntity);

        GroceryItem groceryItemToReturn = GroceryItem.builder()
                .product(Product.builder()
                        .category("Category")
                        .subcategory("Subcategory")
                        .build())
                .build();

        // then
        GroceryItem actual = groceryService.addGroceryItem(groceryId, requestModel);
        GroceryItem expected = groceryItemToReturn;
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Given grocery id is not found" +
            "When add groceries item is called" +
            "Then it returns not found exception" +
            "And it does not save")
    void addGroceryItem_groceryIdIsNotFound() {
        Integer groceryId = MOCK_GROCERY_ID;
        GroceryItemRequestModel requestModel = GroceryItemRequestModel.builder()
                .productId(MOCK_PRODUCT_ID)
                .build();

        // given
        Optional<GroceryEntity> notFoundGroceryEntity = Optional.empty();

        // when
        when(groceryRepository.findById(MOCK_GROCERY_ID))
                .thenReturn(notFoundGroceryEntity);

        // then
        assertThrows(NotFoundException.class, () -> groceryService.addGroceryItem(groceryId, requestModel));
        verify(groceryItemRepository, never()).save(any(GroceryItemEntity.class));
    }

    @Test
    @DisplayName("Given grocery is found" +
            "And product is not found" +
            "When add groceries item is called" +
            "Then it returns not found exception" +
            "And it does not save")
    void addGroceryItem_productIdIsNotFound() {
        Integer groceryId = MOCK_GROCERY_ID;
        GroceryItemRequestModel requestModel = GroceryItemRequestModel.builder()
                .productId(MOCK_PRODUCT_ID)
                .build();

        // given
        Optional<ProductEntity> notFoundProductEntity = Optional.empty();

        // when
        when(groceryRepository.findById(MOCK_GROCERY_ID))
                .thenReturn(Optional.of(GroceryEntity.builder().build()));
        when(productRepository.findById(MOCK_PRODUCT_ID))
                .thenReturn(notFoundProductEntity);

        // then
        assertThrows(NotFoundException.class, () -> groceryService.addGroceryItem(groceryId, requestModel));
        verify(groceryItemRepository, never()).save(any(GroceryItemEntity.class));
    }
}