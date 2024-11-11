package com.joliest.portfolios.groceryapi.testHelper;


import com.joliest.portfolios.groceryapi.domain.entity.CategoryEntity;
import com.joliest.portfolios.groceryapi.domain.entity.ProductEntity;
import com.joliest.portfolios.groceryapi.domain.entity.SubcategoryEntity;
import com.joliest.portfolios.groceryapi.domain.repository.ProductRepository;
import com.joliest.portfolios.groceryapi.domain.repository.SubcategoryRepository;
import com.joliest.portfolios.groceryapi.model.ProductImport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

import static com.joliest.portfolios.groceryapi.testHelper.CategoryTestHelper.CATEGORY_TEST_ID_1;

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
    private SubcategoryRepository subcategoryRepository;

    public ProductEntity setupProducts() {
        storeTestHelper.setupStore();
        ProductEntity productEntityToSave = createProduct();
        return productRepository.save(productEntityToSave);
    }

    // special use in GroceryControllerIntegrationTest
    public ProductEntity setupProductsWithoutStore() {
        ProductEntity productEntityToSave = createProduct();
        return productRepository.save(productEntityToSave);
    }

    public ProductEntity createProduct() {
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
}
