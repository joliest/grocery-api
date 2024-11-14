package com.joliest.portfolios.groceryapi.controller;

import org.junit.jupiter.api.BeforeAll;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;

public abstract class BaseIntegrationTest {
    private static final String INIT_SCRIPT = "integration-testing/initScript.sql";
    private static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:16.0")
            .withUsername("username")
            .withPassword("password")
            .withInitScript(INIT_SCRIPT);

    @DynamicPropertySource
    public static void properties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
        registry.add("spring.datasource.username", postgreSQLContainer::getUsername);
        registry.add("spring.datasource.password", postgreSQLContainer::getPassword);
    }

    @BeforeAll
    public static void init() {
        postgreSQLContainer.start();
    }
}
