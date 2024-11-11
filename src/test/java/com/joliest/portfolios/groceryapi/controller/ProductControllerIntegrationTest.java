package com.joliest.portfolios.groceryapi.controller;

import com.joliest.portfolios.groceryapi.model.ProductImport;
import com.joliest.portfolios.groceryapi.testHelper.ProductTestHelper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Description;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static com.joliest.portfolios.groceryapi.testHelper.CategoryTestHelper.MOCK_CATEGORY_NAME_1;
import static com.joliest.portfolios.groceryapi.testHelper.ProductTestHelper.PRODUCTS_URI;
import static com.joliest.portfolios.groceryapi.testHelper.ProductTestHelper.PRODUCT_IMPORT_URI;
import static com.joliest.portfolios.groceryapi.testHelper.TestContainerConstants.getPostgreSqlContainer;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ProductControllerIntegrationTest {
    @Autowired
    private ProductTestHelper productTestHelper;

    @Autowired
    private TestRestTemplate restTemplate;

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgres = getPostgreSqlContainer();

    @Test
    @Order(1)
    @DisplayName("Get All Products")
    @Description("Scenario: Happy Path" +
            "Given GET v1/products is the endpoint" +
            "When GET endpoint is called" +
            "Then it will send the list of products")
    public void getProducts() {
        // setup
        productTestHelper.setupProducts();

        // when
        ParameterizedTypeReference<List<ProductImport>> responseType = new ParameterizedTypeReference<>() {};
        ResponseEntity<List<ProductImport>> response = restTemplate.exchange(PRODUCTS_URI, HttpMethod.GET, null, responseType);

        // then
        List<ProductImport> productImportList = response.getBody();
        assertThat(productImportList).isNotNull();
        assertThat(productImportList.get(0).getId()).isNotNull();
        assertThat(productImportList.get(0).getName()).isEqualTo("New product 1");
        assertThat(productImportList.get(0).getCategory()).isEqualTo(MOCK_CATEGORY_NAME_1);
        assertThat(productImportList.get(0).getSubcategory()).isEqualTo("New Product Sub Category");
        assertThat(productImportList.get(0).getPurchaseHistoryList()).isInstanceOf(List.class);
    }

    @Test
    @Order(2)
    @DisplayName("Post Products")
    @Description("Scenario: Happy Path" +
            "Given POST v1/products/import is the endpoint" +
            "When POST endpoint is called with correct request body" +
            "Then it will send a response of saved products")
    public void importProducts() {
        // given
        List<ProductImport> requestBody = productTestHelper.createRequestBody();

        // when
        ResponseEntity<List<ProductImport>> response = restTemplate.exchange(PRODUCT_IMPORT_URI, HttpMethod.POST, new HttpEntity<>(requestBody), new ParameterizedTypeReference<>() {});

        // then
        List<ProductImport> importedProduct = response.getBody();

        assertThat(importedProduct).isNotNull();
        assertThat(importedProduct.get(0).getId()).isNotNull();
        assertThat(importedProduct.get(0).getName()).isEqualTo("New product 1");
        assertThat(importedProduct.get(0).getCategory()).isEqualTo("New Product Category");
        assertThat(importedProduct.get(0).getSubcategory()).isEqualTo("New Product Sub Category");
        assertThat(importedProduct.get(0).getStore()).isEqualTo("New store name");
        assertThat(importedProduct.get(0).getPrice()).isEqualTo(100L);
    }
}