package com.joliest.portfolios.groceryapi.testHelper;


import com.joliest.portfolios.groceryapi.domain.entity.CategoryEntity;
import com.joliest.portfolios.groceryapi.domain.entity.ProductEntity;
import com.joliest.portfolios.groceryapi.domain.entity.SubcategoryEntity;
import com.joliest.portfolios.groceryapi.domain.repository.CategoryRepository;
import com.joliest.portfolios.groceryapi.domain.repository.GroceryItemRepository;
import com.joliest.portfolios.groceryapi.domain.repository.ProductRepository;
import com.joliest.portfolios.groceryapi.domain.repository.PurchaseHistoryRepository;
import com.joliest.portfolios.groceryapi.domain.repository.SubcategoryRepository;
import com.joliest.portfolios.groceryapi.model.ProductImport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

import java.util.Collections;
import java.util.List;

import static com.joliest.portfolios.groceryapi.testHelper.CategoryTestHelper.CATEGORY_TEST_ID_1;
import static com.joliest.portfolios.groceryapi.testHelper.StoreTestHelper.MOCK_STORE_NAME;
import static java.util.Arrays.asList;

@Service
public class ProductTestHelper {

    public static String PRODUCTS_URI = "/v1/products";
    public static String PRODUCT_IMPORT_URI = "/v1/products/import";
    public static final String MOCK_STRING_DATE_2 = "05-13-2023";

    // newly setup data are always set to 1
    public static final Integer SUB_CATEGORY_TEST_ID = 1;

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private StoreTestHelper storeTestHelper;
    @Autowired
    private CategoryTestHelper categoryTestHelper;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private SubcategoryRepository subcategoryRepository;
    @Autowired
    private PurchaseHistoryRepository purchaseHistoryRepository;
    @Autowired
    private GroceryItemRepository groceryItemRepository;
    @Autowired
    private WebTestClient webTestClient;
    public void setupProducts() {
        ProductEntity productEntityToSave = createProduct();
        productRepository.save(productEntityToSave);
    }

    public ProductEntity createProduct() {
        storeTestHelper.setupStore();
        categoryTestHelper.setupCategories();
        setupSubcategory();

        CategoryEntity category = CategoryEntity.builder()
                .id(CATEGORY_TEST_ID_1)
                .build();
        return ProductEntity.builder()
                .name("New product 1")
                .category(category)
                .subcategory(SubcategoryEntity.builder()
                        .category(category)
                        .id(SUB_CATEGORY_TEST_ID)
                        .build())
                .build();
    }

    public void setupSubcategory() {
        SubcategoryEntity subcategory = SubcategoryEntity.builder()
                .category(CategoryEntity.builder()
                        .id(CATEGORY_TEST_ID_1)
                        .build())
                .name("New Product Sub Category")
                .description("Subcategory Description")
                .build();

        // ID is automatically set to 1
        subcategoryRepository.save(subcategory);
    }

    public List<ProductImport> createRequestBody() {
        return Collections.singletonList(ProductImport.builder()
                .name("New product 1")
                .link("http://test/new-product-1")
                .category("New Product Category")
                .subcategory("New Product Sub Category")
                .price(100L)
                .store("New store name")
                .datePurchased(MOCK_STRING_DATE_2)
                .build());
    }

    public void cleanupTest() {
        purchaseHistoryRepository.deleteAll();
        groceryItemRepository.deleteAll();
        productRepository.deleteAll();
        subcategoryRepository.deleteAll();
        categoryRepository.deleteAll();
        storeTestHelper.cleanupStore();
    }

    /**
     * TODO: setup products no longer create a store.
     * I need to import product so that i will be able to create a store
     * i used web test client kasi mas mabilis
     *
     * (!) Unfortunately, the store response from import is a string name :(
     */
    public WebTestClient.ResponseSpec performProductImport() {
        categoryTestHelper.setupCategories();
        this.setupSubcategory();
        storeTestHelper.setupStore();

        // given
        List<ProductImport> requestBody = this.createRequestBody();
        return webTestClient
                .post()
                .uri(PRODUCT_IMPORT_URI)
                .body(BodyInserters.fromValue(requestBody))
                .exchange();
    }

    public Integer extractIdFromResponseSpecByPath(WebTestClient.ResponseSpec responseSpec, String path) {
        final Integer[] values = {0};
        responseSpec.expectBody()
                .jsonPath(path)
                .value(id -> {
                    values[0] = (Integer) id;
                });
        return values[0];
    }

}
