package com.joliest.portfolios.groceryapi.testHelper;


import com.joliest.portfolios.groceryapi.domain.entity.CategoryEntity;
import com.joliest.portfolios.groceryapi.domain.entity.ProductEntity;
import com.joliest.portfolios.groceryapi.domain.entity.SubcategoryEntity;
import com.joliest.portfolios.groceryapi.domain.repository.ProductRepository;
import com.joliest.portfolios.groceryapi.model.ProductImport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

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
    private SubcategoryTestHelper subcategoryTestHelper;

    public ProductEntity setupProduct(String baseName) {
        storeTestHelper.setupStore(format(baseName, "store-name"));
        CategoryEntity categoryEntity = categoryTestHelper.setupCategory(format(baseName, "category-name"));
        SubcategoryEntity subcategoryEntity = subcategoryTestHelper.setupSubcategoryWithCategory(format(baseName, "subcategory-name"), categoryEntity.getId());
        return setupProductsWithCategoryAndSubcategory(baseName, categoryEntity, subcategoryEntity);
    }

    // special use in GroceryControllerIntegrationTest
    public ProductEntity setupProductsWithCategoryAndSubcategory(String name, CategoryEntity category, SubcategoryEntity subcategory) {
        ProductEntity productEntityToSave = createProduct(name, category, subcategory);
        return productRepository.save(productEntityToSave);
    }

    public ProductEntity createProduct(String name, CategoryEntity category, SubcategoryEntity subcategory) {
        return ProductEntity.builder()
                .name(name)
                .category(category)
                .subcategory(SubcategoryEntity.builder()
                        .category(category)
                        .id(subcategory.getId())
                        .build())
                .build();
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

    private String format(String baseName, String suffix) {
        return String.format("%s-%s", baseName, suffix);
    }
}
