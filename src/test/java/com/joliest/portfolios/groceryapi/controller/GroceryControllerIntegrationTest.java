package com.joliest.portfolios.groceryapi.controller;

import com.joliest.portfolios.groceryapi.domain.entity.ProductEntity;
import com.joliest.portfolios.groceryapi.model.Grocery;
import com.joliest.portfolios.groceryapi.model.GroceryRequestModel;
import com.joliest.portfolios.groceryapi.model.GroceryItem;
import com.joliest.portfolios.groceryapi.model.GroceryItemRequestModel;
import com.joliest.portfolios.groceryapi.testHelper.BaseIntegrationTest;
import com.joliest.portfolios.groceryapi.testHelper.ProductTestHelper;
import com.joliest.portfolios.groceryapi.testHelper.StoreTestHelper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.Description;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class GroceryControllerIntegrationTest extends BaseIntegrationTest {
    static Integer GROCERY_ID = 1; // newly created grocery in this file will be 1
    static String GROCERY_URI = "/v1/groceries";
    static String GROCERY_BY_ID_URI = "/v1/groceries/%s";
    static String GROCERY_ITEM_URI = "/v1/groceries/%s/item";
    static String NAME_SAMPLE = "New Grocery";
    static String DESCRIPTION_SAMPLE = "Description";
    static String STORE_NAME = "add-grocery-store";
    static String PRODUCT_NAME = "add-grocery-product";

    @Autowired
    private ProductTestHelper productTestHelper;

    @Autowired
    private StoreTestHelper storeTestHelper;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    @Order(1)
    @DisplayName("Post Grocery")
    @Description("Scenario: Happy Path" +
            "Given POST v1/groceries is the endpoint" +
            "When POST endpoint is called with correct request body" +
            "Then it will send a response of saved grocery")
    public void addGrocery(){
        // given
        GroceryRequestModel requestBody = createGroceryRequestBody();

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
        assertThat(productImportList.get(0).getList()).isInstanceOf(List.class);
    }

    @Test
    @Order(3)
    @DisplayName("Get Grocery by Id")
    @Description("Scenario: Happy Path" +
            "Given a valid grocery id is provided" +
            "And GET v1/groceries/{groceryId} is the endpoint" +
            "When the endpoint is called" +
            "Then it will send a grocery")
    public void getGroceryById() {
        // given
        String urlWithId = String.format(GROCERY_BY_ID_URI, GROCERY_ID);

        // when
        ResponseEntity<Grocery> response = restTemplate.exchange(
                urlWithId,
                HttpMethod.GET,
                null,
                Grocery.class);

        // then
        Grocery grocery = response.getBody();
        assertThat(grocery).isNotNull();
        assertThat(grocery.getId()).isNotNull();
        assertThat(grocery.getName()).isEqualTo(NAME_SAMPLE);
        assertThat(grocery.getDescription()).isEqualTo(DESCRIPTION_SAMPLE);
        assertThat(grocery.getList()).isInstanceOf(List.class);
    }

    @Test
    @Order(4)
    @DisplayName("Post Grocery Item")
    @Description("Scenario: Happy Path" +
            "Given POST v1/groceries/{groceryId}/item is the endpoint" +
            "When POST endpoint is called with correct request body" +
            "Then it will send a response of saved grocery item")
    public void addGroceryItem() {
        // setup
        ProductEntity productFromSetup = productTestHelper.setupProduct(PRODUCT_NAME);

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

    private GroceryRequestModel createGroceryRequestBody() {
        return  GroceryRequestModel.builder()
                .name(NAME_SAMPLE)
                .description(DESCRIPTION_SAMPLE)
                .build();
    }
}