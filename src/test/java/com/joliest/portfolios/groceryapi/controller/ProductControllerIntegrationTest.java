package com.joliest.portfolios.groceryapi.controller;

import com.joliest.portfolios.groceryapi.domain.entity.CategoryEntity;
import com.joliest.portfolios.groceryapi.domain.entity.ProductEntity;
import com.joliest.portfolios.groceryapi.domain.entity.StoreEntity;
import com.joliest.portfolios.groceryapi.domain.entity.SubcategoryEntity;
import com.joliest.portfolios.groceryapi.domain.repository.CategoryRepository;
import com.joliest.portfolios.groceryapi.domain.repository.ProductRepository;
import com.joliest.portfolios.groceryapi.domain.repository.StoreRepository;
import com.joliest.portfolios.groceryapi.domain.repository.SubcategoryRepository;
import com.joliest.portfolios.groceryapi.model.Product;
import com.joliest.portfolios.groceryapi.model.Products;
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
import java.util.Map;

import static com.joliest.portfolios.groceryapi.utils.DateUtil.convertStrToLocalDateTime;
import static java.util.Arrays.asList;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class ProductControllerIntegrationTest {
    private static final String MOCK_STRING_DATE_2 = "05-13-2023";
    private static final String MOCK_STORE_NAME = "sample store";
    private static final String MOCK_CATEGORY_NAME = "sample category";

    static String PRODUCTS_URI = "/v1/products";

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private StoreRepository storeRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private SubcategoryRepository subcategoryRepository;

    @Autowired
    private WebTestClient webTestClient;

    @Test
    @DisplayName("Get All Products")
    @Description("Scenario: Happy Path" +
            "Given GET v1/products is the endpoint" +
            "When GET endpoint is called" +
            "Then it will send the list of products")
    public void getProducts() {
        // setup
        Integer newId = setupProducts();

        // when
        WebTestClient.BodyContentSpec response = webTestClient
                .get()
                .uri(PRODUCTS_URI)
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBody();

        cleanupByProductId(newId);
    }

    @Test
    @DisplayName("Post Products")
    @Description("Scenario: Happy Path" +
            "Given POST v1/products is the endpoint" +
            "When POST endpoint is called with correct request body" +
            "Then it will send a response of saved products")
    public void postProduct() {
        // setup
        int categoryId = setupCategory();
        setupSubcategory(categoryId);
        setupStore();

        // given
        Products requestBody = createRequestBody();

        // when & then
        WebTestClient.BodyContentSpec response = webTestClient
                .post()
                .uri(PRODUCTS_URI)
                .body(BodyInserters.fromValue(requestBody))
                .exchange()
                .expectStatus()
                .isCreated()
                .expectBody();

        response.jsonPath("$[0].id").isNotEmpty();
        response.jsonPath("$[0].name").isEqualTo("New product 1");
        response.jsonPath("$[0].category").isEqualTo("New Product Category");
        response.jsonPath("$[0].subcategory").isEqualTo("New Product Sub Category");
        response.jsonPath("$[0].store").isEqualTo(MOCK_STORE_NAME);
        response.jsonPath("$[0].price").isEqualTo(100L);

        // cleanup
        cleanupPostProduct(response);
    }


    private Integer setupProducts() {
        ProductEntity productEntityToSave = createProduct();
        return productRepository.save(productEntityToSave).getId();
    }

    private ProductEntity createProduct() {
        int storeId = setupStore();
        int categoryId = setupCategory();
        int subcategoryId = setupSubcategory(categoryId);

        CategoryEntity category = CategoryEntity.builder()
                .id(categoryId)
                .build();
        return ProductEntity.builder()
                .name("New product 1")
                .link("http://test/new-product-1")
                .category(category)
                .subcategory(SubcategoryEntity.builder()
                        .category(category)
                        .id(subcategoryId)
                        .build())
                .price(100L)
                .store(StoreEntity.builder()
                        .id(storeId)
                        .build())
                .datePurchased(convertStrToLocalDateTime(MOCK_STRING_DATE_2))
                .build();
    }

    private int setupStore() {
        StoreEntity storeEntityToSave = StoreEntity.builder()
                .name(MOCK_STORE_NAME)
                .build();
        return storeRepository.save(storeEntityToSave).getId();
    }

    private int setupCategory() {
        CategoryEntity categoryEntityToSave = CategoryEntity.builder()
                .name("New Product Category")
                .build();
        return categoryRepository.save(categoryEntityToSave).getId();
    }

    private Integer setupSubcategory(Integer categoryId) {
        CategoryEntity categoryEntity = categoryRepository.findById(categoryId).get();
        SubcategoryEntity subcategory = SubcategoryEntity.builder()
                .category(categoryEntity)
                .name("New Product Sub Category")
                .description("Desc 1")
                .build();
        return subcategoryRepository.save(subcategory).getId();
    }

    private Products createRequestBody() {
        return Products.builder()
                .products(asList(Product.builder()
                        .name("New product 1")
                        .link("http://test/new-product-1")
                        .category("New Product Category")
                        .subcategory("New Product Sub Category")
                        .price(100L)
                        .store(MOCK_STORE_NAME)
                        .datePurchased(MOCK_STRING_DATE_2)
                        .build()))
                .build();
    }

    private void cleanupPostProduct(WebTestClient.BodyContentSpec response) {
        response.jsonPath("$").value((result) -> {
            List<Map<String, Object>> products = (List<Map<String, Object>>) result;
            products.forEach(product -> {
                cleanupByProductId((Integer) product.get("id"));
            });
        });
    }

    private void cleanupByProductId(int id) {
        ProductEntity productEntity = productRepository.findById(id).get();
        productRepository.deleteById(id);
        storeRepository.deleteById(productEntity.getStore().getId());
        subcategoryRepository.deleteById(productEntity.getSubcategory().getId());
        categoryRepository.deleteById(productEntity.getCategory().getId());
    }
}