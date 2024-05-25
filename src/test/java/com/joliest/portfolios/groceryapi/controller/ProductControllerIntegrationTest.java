package com.joliest.portfolios.groceryapi.controller;

import com.joliest.portfolios.groceryapi.domain.entity.CategoryEntity;
import com.joliest.portfolios.groceryapi.domain.entity.ProductEntity;
import com.joliest.portfolios.groceryapi.domain.entity.StoreEntity;
import com.joliest.portfolios.groceryapi.domain.repository.CategoryRepository;
import com.joliest.portfolios.groceryapi.domain.repository.ProductRepository;
import com.joliest.portfolios.groceryapi.domain.repository.StoreRepository;
import com.joliest.portfolios.groceryapi.model.Product;
import com.joliest.portfolios.groceryapi.model.Products;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Description;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
    private WebTestClient webTestClient;

    @Test
    @Description("Get All Products")
    public void getProducts() {
        // setup
        Integer newId = setupProducts();

        // when
        WebTestClient.BodyContentSpec response = webTestClient
                .get()
                .uri(PRODUCTS_URI)
                .exchange() // make the call to the endpoint
                .expectStatus()
                .is2xxSuccessful()
                .expectBody();

        cleanupProduct(newId);
    }

    @Test
    @Description("Post Product")
    public void postProduct() {
        // setup
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
        response.jsonPath("$[0].store").isEqualTo(MOCK_STORE_NAME);
        response.jsonPath("$[0].price").isEqualTo(100L);

        // cleanup
        cleanupPostProduct(response);
    }


    private Integer setupProducts() {
        ProductEntity productEntityToSave = createProduct();
        return productRepository.save(productEntityToSave).getId();
    }

    private int setupStore() {
        StoreEntity storeEntityToSave = StoreEntity.builder()
                .name(MOCK_STORE_NAME)
                .build();
        return storeRepository.save(storeEntityToSave).getId();
    }

    private int setupCategory() {
        CategoryEntity categoryEntityToSave = CategoryEntity.builder()
                .name(MOCK_CATEGORY_NAME)
                .build();
        return categoryRepository.save(categoryEntityToSave).getId();
    }

    private ProductEntity createProduct() {
        int storeId = setupStore();
        int categoryId = setupCategory();

        return ProductEntity.builder()
                .name("New product 1")
                .link("http://test/new-product-1")
                .category(CategoryEntity.builder()
                        .id(categoryId)
                        .build())
                .subcategory("New Product Sub Category")
                .price(100L)
                .store(StoreEntity.builder()
                        .id(storeId)
                        .build())
                .datePurchased(convertStrToLocalDateTime(MOCK_STRING_DATE_2))
                .build();
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
                cleanupProduct((Integer) product.get("id"));
                cleanupStore((String) product.get("store"));
                cleanupCategory((String) product.get("category"));
            });
        });
    }

    private void cleanupProduct(int id) {
        Optional<ProductEntity> testProductToRemove = productRepository.findById(id);
        int storeId = testProductToRemove.get().getStore().getId();
        int categoryId = testProductToRemove.get().getCategory().getId();
        productRepository.deleteById(id);
        storeRepository.deleteById(storeId);
        categoryRepository.deleteById(categoryId);
    }
    private void cleanupStore(String name) {
        storeRepository.removeByName(name);
    }
    private void cleanupCategory(String name) {
        categoryRepository.removeByName(name);
    }
}