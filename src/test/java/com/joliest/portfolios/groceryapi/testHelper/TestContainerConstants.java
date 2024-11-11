package com.joliest.portfolios.groceryapi.testHelper;

import org.testcontainers.containers.PostgreSQLContainer;

public class TestContainerConstants {
    public static final String INIT_SCRIPT = "integration-testing/initScript.sql";
    public static PostgreSQLContainer<?> getPostgreSqlContainer() {
        return new PostgreSQLContainer<>("postgres:16.0")
                .withInitScript(INIT_SCRIPT);
    }
}
