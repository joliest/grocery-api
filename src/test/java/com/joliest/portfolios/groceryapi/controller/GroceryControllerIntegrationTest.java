package com.joliest.portfolios.groceryapi.controller;

import com.joliest.portfolios.groceryapi.domain.repository.GroceryRepository;
import com.joliest.portfolios.groceryapi.model.Grocery;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Description;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class GroceryControllerIntegrationTest {

    static String GROCERY_URI = "/v1/groceries";
    static String NAME_SAMPLE = "New Grocery";
    static String DESCRIPTION_SAMPLE = "Description";

    @Autowired
    private GroceryRepository groceryRepository;

    @Autowired
    private WebTestClient webTestClient;

    @BeforeEach
    void startFresh() {
        groceryRepository.deleteAll();
    }

    @Test
    @DisplayName("Post Products")
    @Description("Scenario: Happy Path" +
            "Given POST v1/groceries is the endpoint" +
            "When POST endpoint is called with correct request body" +
            "Then it will send a response of saved grocery")
    public void importProducts() {
        // given
        Grocery requestBody = Grocery.builder()
                .name(NAME_SAMPLE)
                .description(DESCRIPTION_SAMPLE)
                .build();

        // when & then
        WebTestClient.BodyContentSpec response = webTestClient
                .post()
                .uri(GROCERY_URI)
                .body(BodyInserters.fromValue(requestBody))
                .exchange()
                .expectStatus()
                .isCreated()
                .expectBody();

        response.jsonPath("$.id").isNotEmpty();
        response.jsonPath("$.name").isEqualTo(NAME_SAMPLE);
        response.jsonPath("$.description").isEqualTo(DESCRIPTION_SAMPLE);
    }
}