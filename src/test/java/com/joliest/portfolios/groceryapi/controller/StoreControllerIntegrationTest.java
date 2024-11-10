package com.joliest.portfolios.groceryapi.controller;

import com.joliest.portfolios.groceryapi.model.Store;
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

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class StoreControllerIntegrationTest extends BaseIntegrationTest {
    static String STORES_URI = "/v1/stores";

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private StoreTestHelper storeTestHelper;

    @Test
    @Order(1)
    @DisplayName("Post Stores")
    @Description("Scenario: Happy Path" +
            "Given POST v1/stores is the endpoint" +
            "When POST endpoint is called with correct request body" +
            "Then it will send a response of saved stores")
    public void postStores() {
        List<Store> requestBody = storeTestHelper.createRequestBody();

        ResponseEntity<List<Store>> response = restTemplate.exchange(STORES_URI, HttpMethod.POST, new HttpEntity<>(requestBody), new ParameterizedTypeReference<>() {});
        List<Store> stores = response.getBody();

        assertThat(stores.get(0).getName()).isNotNull();
        assertThat(stores.get(0).getDescription()).isNotNull();

        assertThat(stores.get(1).getName()).isNotNull();
        assertThat(stores.get(1).getDescription()).isNotNull();

    }

    @Test
    @Order(2)
    @DisplayName("Get All Stores")
    @Description("Scenario: Happy Path" +
            "Given GET v1/stores is the endpoint" +
            "When GET endpoint is called" +
            "Then it will send the list of stores")
    public void getStores() {
        ParameterizedTypeReference<List<Store>> responseType = new ParameterizedTypeReference<>() {};
        ResponseEntity<List<Store>> response = restTemplate.exchange(STORES_URI, HttpMethod.GET, null, responseType);
        List<Store> stores = response.getBody();

        assertThat(stores.get(0).getName()).isNotNull();
        assertThat(stores.get(0).getDescription()).isNotNull();

        assertThat(stores.get(1).getName()).isNotNull();
        assertThat(stores.get(1).getDescription()).isNotNull();
    }
}