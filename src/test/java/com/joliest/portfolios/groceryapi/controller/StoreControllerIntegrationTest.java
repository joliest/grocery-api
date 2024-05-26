package com.joliest.portfolios.groceryapi.controller;

import com.joliest.portfolios.groceryapi.domain.entity.StoreEntity;
import com.joliest.portfolios.groceryapi.domain.repository.StoreRepository;
import com.joliest.portfolios.groceryapi.model.Store;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Description;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class StoreControllerIntegrationTest {
    static String STORES_URI = "/v1/stores";

    @Autowired
    private StoreRepository storeRepository;

    @Autowired
    private WebTestClient webTestClient;

    @Test
    @DisplayName("Get All Stores")
    @Description("Scenario: Happy Path" +
            "Given GET v1/stores is the endpoint" +
            "When GET endpoint is called" +
            "Then it will send the list of stores")
    public void getStores() {
        List<Integer> storeIds = setupStores();
        webTestClient
                .get()
                .uri(STORES_URI)
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBodyList(Store.class);
        cleanup(storeIds);
    }

    @Test
    @DisplayName("Post Stores")
    @Description("Scenario: Happy Path" +
            "Given POST v1/stores is the endpoint" +
            "When POST endpoint is called with correct request body" +
            "Then it will send a response of saved stores")
    public void postStores() {
        List<Store> requestBody = createRequestBody();
        webTestClient
                .post()
                .uri(STORES_URI)
                .body(BodyInserters.fromValue(requestBody))
                .exchange()
                .expectStatus()
                .isCreated()
                .expectBodyList(Store.class)
                //cleanup
                .consumeWith(result -> {
                    List<Integer> storeIds = result.getResponseBody()
                            .stream()
                            .map(Store::getId)
                            .collect(Collectors.toList());
                    cleanup(storeIds);
                });
    }


    private List<Integer> setupStores() {
        StoreEntity store1 = StoreEntity.builder()
                .name("Store 1")
                .description("Desc 1")
                .build();
        StoreEntity store2 = StoreEntity.builder()
                .name("Store 2")
                .description("Desc 2")
                .build();
        List<StoreEntity> storesToSave = asList(store1, store2);
        List<StoreEntity> savedStore = storeRepository.saveAll(storesToSave);
        return savedStore.stream()
                .map(StoreEntity::getId)
                .collect(Collectors.toList());
    }

    private List<Store> createRequestBody() {
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

    private void cleanup(List<Integer> ids) {
        storeRepository.deleteAllById(ids);
    }
}