package com.joliest.portfolios.groceryapi.controller;

import com.joliest.portfolios.groceryapi.domain.entity.ProductEntity;
import com.joliest.portfolios.groceryapi.model.Grocery;
import com.joliest.portfolios.groceryapi.model.GroceryRequestModel;
import com.joliest.portfolios.groceryapi.model.GroceryItem;
import com.joliest.portfolios.groceryapi.model.GroceryItemRequestModel;
import com.joliest.portfolios.groceryapi.testHelper.ProductTestHelper;
import com.joliest.portfolios.groceryapi.testHelper.StoreTestHelper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.Description;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;

import java.util.List;

import static com.joliest.portfolios.groceryapi.testHelper.StoreTestHelper.MOCK_STORE_NAME;
import static org.assertj.core.api.Assertions.assertThat;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class GroceryControllerIntegrationTest extends BaseIntegrationTest {

    static Integer GROCERY_ID = 1; // newly created grocery in this file will be 1
    static String GROCERY_URI = "/v1/groceries";
    static String GROCERY_ITEM_URI = "/v1/groceries/%s/item";
    static String NAME_SAMPLE = "New Grocery";
    static String DESCRIPTION_SAMPLE = "Description";

    @Autowired
    private ProductTestHelper productTestHelper;

    @Autowired
    private StoreTestHelper storeTestHelper;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    @Order(1)
    @DisplayName("Post Grocery")
    @Rollback
    @Description("Scenario: Happy Path" +
            "Given POST v1/groceries is the endpoint" +
            "When POST endpoint is called with correct request body" +
            "Then it will send a response of saved grocery")
    public void addGrocery(){
        // store will be used across this test file
        Integer storeId = storeTestHelper.setupStore().getId();

        // given
        GroceryRequestModel requestBody = createGroceryRequestBody(storeId);

        // when
        ResponseEntity<Grocery> response = restTemplate.exchange(
                GROCERY_URI,
                HttpMethod.POST,
                new HttpEntity<>(requestBody),
                Grocery.class);

        // then
        Grocery addedGrocery = response.getBody();

        assertThat(addedGrocery).isNotNull();
        assertThat(addedGrocery.getId()).isNotNull(); // = 1
        assertThat(addedGrocery.getName()).isEqualTo(NAME_SAMPLE);
        assertThat(addedGrocery.getDescription()).isEqualTo(DESCRIPTION_SAMPLE);
        assertThat(addedGrocery.getStore().getName()).isEqualTo(MOCK_STORE_NAME);
        assertThat(addedGrocery.getList()).isInstanceOf(List.class);
    }

    @Test
    @Order(2)
    @DisplayName("Get Groceries")
    @Description("Scenario: Happy Path" +
            "Given GET v1/groceries is the endpoint" +
            "When the endpoint is called" +
            "Then it will send a response of saved grocery list")
    public void getGroceries() {
        // when
        ParameterizedTypeReference<List<Grocery>> responseType = new ParameterizedTypeReference<>() {};
        ResponseEntity<List<Grocery>> response = restTemplate.exchange(
                GROCERY_URI,
                HttpMethod.GET,
                null,
                responseType);

        // then
        List<Grocery> productImportList = response.getBody();
        assertThat(productImportList).isNotNull();
        assertThat(productImportList.get(0).getId()).isNotNull();
        assertThat(productImportList.get(0).getName()).isEqualTo(NAME_SAMPLE);
        assertThat(productImportList.get(0).getDescription()).isEqualTo(DESCRIPTION_SAMPLE);
        assertThat(productImportList.get(0).getStore().getName()).isEqualTo(MOCK_STORE_NAME);
        assertThat(productImportList.get(0).getList()).isInstanceOf(List.class);
    }

    @Test
    @Order(3)
    @DisplayName("Post Grocery Item")
    @Description("Scenario: Happy Path" +
            "Given POST v1/groceries/{groceryId}/item is the endpoint" +
            "When POST endpoint is called with correct request body" +
            "Then it will send a response of saved grocery item")
    public void addGroceryItem() {
        // setup
        ProductEntity productFromSetup = productTestHelper.setupProductsWithoutStore();

        // given
        GroceryItemRequestModel requestBody = GroceryItemRequestModel.builder()
                .productId(productFromSetup.getId())
                .actualPrice(100L)
                .notes("Sample Grocery Item Notes")
                .estimatedPrice(0L)
                .build();

        // when
        ResponseEntity<GroceryItem> response = restTemplate.exchange(
                String.format(GROCERY_ITEM_URI, GROCERY_ID),
                HttpMethod.POST,
                new HttpEntity<>(requestBody),
                GroceryItem.class);

        // then
        GroceryItem newGroceryItem = response.getBody();
        assertThat(newGroceryItem).isNotNull();
        assertThat(newGroceryItem.getId()).isNotNull();
        assertThat(newGroceryItem.getNotes()).isEqualTo("Sample Grocery Item Notes");
        assertThat(newGroceryItem.getEstimatedPrice()).isEqualTo(0L);
        assertThat(newGroceryItem.getActualPrice()).isEqualTo(100L);
    }

    private GroceryRequestModel createGroceryRequestBody(Integer storeId) {
        return  GroceryRequestModel.builder()
                .name(NAME_SAMPLE)
                .storeId(storeId)
                .description(DESCRIPTION_SAMPLE)
                .build();
    }
}