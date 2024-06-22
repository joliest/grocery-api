package com.joliest.portfolios.groceryapi.service;

import com.joliest.portfolios.groceryapi.domain.entity.CategoryEntity;
import com.joliest.portfolios.groceryapi.domain.entity.ProductEntity;
import com.joliest.portfolios.groceryapi.domain.entity.PurchaseHistoryEntity;
import com.joliest.portfolios.groceryapi.domain.entity.StoreEntity;
import com.joliest.portfolios.groceryapi.domain.entity.SubcategoryEntity;
import com.joliest.portfolios.groceryapi.domain.repository.CategoryRepository;
import com.joliest.portfolios.groceryapi.domain.repository.ProductRepository;
import com.joliest.portfolios.groceryapi.domain.repository.PurchaseHistoryRepository;
import com.joliest.portfolios.groceryapi.domain.repository.StoreRepository;
import com.joliest.portfolios.groceryapi.domain.repository.SubcategoryRepository;
import com.joliest.portfolios.groceryapi.model.Product;
import com.joliest.portfolios.groceryapi.model.ProductImport;
import com.joliest.portfolios.groceryapi.model.PurchaseHistory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static com.joliest.portfolios.groceryapi.utils.DateUtil.convertStrToLocalDateTime;
import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {
    @Mock
    private ProductRepository productRepository;
    @Mock
    private StoreRepository storeRepository;
    @Mock
    private CategoryRepository categoryRepository;
    @Mock
    private SubcategoryRepository subcategoryRepository;
    @Mock
    private PurchaseHistoryRepository purchaseHistoryRepository;
    @InjectMocks
    private ProductService productService;

    private static final String MOCK_STRING_DATE_1 = "04-21-2023";
    private static final String MOCK_STRING_DATE_2 = "05-13-2023";

    @Test
    @DisplayName("When get products is called, Then it'll return the products")
    void getProducts() {
        // when
        List<ProductEntity> productsStream = getMockProductEntities();
        when(productRepository.findAll()).thenReturn(productsStream);
        List<Product> actual = productService.getProducts();

        // then
        List<Product> expected = getMockListOfProducts();
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("when add products, Then it should return the added product")
    void importProduct() {
        // given
        ProductImport productParam = ProductImport.builder()
                .name("New product 1")
                .link("http://test/new-product-1")
                .category("New Product Category")
                .subcategory("New Product Sub Category")
                .price(100L)
                .store("SM Supermarket")
                .datePurchased("05-13-2023")
                .build();

        // when
        CategoryEntity category = CategoryEntity.builder()
                .name("New Product Category")
                .build();
        SubcategoryEntity subcategory = SubcategoryEntity.builder()
                .category(category)
                .name("New Product Sub Category")
                .build();
        StoreEntity storeEntity = StoreEntity.builder()
                .name("SM Supermarket")
                .build();
        ProductEntity productEntityToSave = ProductEntity.builder()
                .name("New product 1")
                .link("http://test/new-product-1")
                .category(category)
                .subcategory(subcategory)
                .price(100L)
                .store(storeEntity)
                .datePurchased(convertStrToLocalDateTime(MOCK_STRING_DATE_2))
                .build();
        ProductEntity createdProductEntity = ProductEntity.builder()
                .id(1001)
                .name("New product 1")
                .link("http://test/new-product-1")
                .category(category)
                .subcategory(subcategory)
                .price(100L)
                .store(storeEntity)
                .datePurchased(convertStrToLocalDateTime(MOCK_STRING_DATE_2))
                .build();

        when(storeRepository.findByName("SM Supermarket"))
                .thenReturn(Optional.of(storeEntity));
        when(categoryRepository.findByName("New Product Category"))
                .thenReturn(Optional.of(category));
        when(subcategoryRepository.findByNameAndCategory(any(String.class), any(CategoryEntity.class)))
                .thenReturn(Optional.of(subcategory));
        when(productRepository.save(productEntityToSave)).thenReturn(createdProductEntity);
        Product newProduct = productService.importProduct(productParam);

        //then
        Product expectedProduct = Product.builder()
                .id(1001)
                .name("New product 1")
                .link("http://test/new-product-1")
                .category("New Product Category")
                .subcategory("New Product Sub Category")
                .price(100L)
                .store("SM Supermarket")
                .datePurchased("05-13-2023")
                .build();
        assertEquals(expectedProduct, newProduct);
        verify(purchaseHistoryRepository).save(any(PurchaseHistoryEntity.class));
    }

    @Test
    @DisplayName("when adding of multiple products, Then it should add the multiple products")
    void importMultipleProducts() {
        // given
        List<ProductImport> products = asList(
                ProductImport.builder()
                        .name("Product Name 1")
                        .category("Category")
                        .link("http://link1")
                        .price(100L)
                        .subcategory("Sub Category")
                        .store("SM Supermarket")
                        .datePurchased("04-21-2023")
                        .build());

        // when
        CategoryEntity category = CategoryEntity.builder()
                .name("Category")
                .build();
        SubcategoryEntity subcategory = SubcategoryEntity.builder()
                .category(category)
                .name("Sub Category")
                .build();
        StoreEntity store = StoreEntity.builder()
                .name("SM Supermarket")
                .build();
        when(storeRepository.findByName("SM Supermarket" ))
                .thenReturn(Optional.of(store));
        when(categoryRepository.findByName("Category"))
                .thenReturn(Optional.of(category));
        when(subcategoryRepository.save(any(SubcategoryEntity.class)))
                .thenReturn(subcategory);
        when(productRepository.save(ProductEntity.builder()
                .name("Product Name 1")
                .category(category)
                .link("http://link1")
                .price(100L)
                .subcategory(subcategory)
                .store(store)
                .datePurchased(convertStrToLocalDateTime(MOCK_STRING_DATE_1))
                .build()
        )).thenReturn(ProductEntity.builder()
                .id(1)
                .name("Product Name 1")
                .category(category)
                .link("http://link1")
                .price(100L)
                .subcategory(subcategory)
                .store(store)
                .datePurchased(convertStrToLocalDateTime(MOCK_STRING_DATE_1))
                .build());
        List<Product> expected = asList(Product.builder()
                .id(1)
                .name("Product Name 1")
                .category("Category")
                .link("http://link1")
                .price(100L)
                .subcategory("Sub Category")
                .store("SM Supermarket")
                .datePurchased("04-21-2023")
                .build());
        List<Product> actual = productService.importMultipleProducts(products);

        // then
        assertEquals(expected, actual);
        verify(purchaseHistoryRepository).save(any(PurchaseHistoryEntity.class));
    }

    @Test
    @DisplayName("when given store is empty, Then it should save the new store")
    void importProductStoreIsEmpty() {
        // given
        ProductImport productParam = ProductImport.builder()
                .name("New product 1")
                .link("http://test/new-product-1")
                .category("New Product Category")
                .subcategory("New Product Sub Category")
                .price(100L)
                .store("SM Supermarket")
                .datePurchased("05-13-2023")
                .build();

        // when
        CategoryEntity category = CategoryEntity.builder()
                .name("New Product Category")
                .build();
        SubcategoryEntity subcategory = SubcategoryEntity.builder()
                .category(category)
                .name("New Product Sub Category")
                .build();
        StoreEntity store = StoreEntity.builder()
                .name("SM Supermarket")
                .build();
        ProductEntity productEntityToSave = ProductEntity.builder()
                .name("New product 1")
                .link("http://test/new-product-1")
                .category(category)
                .subcategory(subcategory)
                .price(100L)
                .store(store)
                .datePurchased(convertStrToLocalDateTime(MOCK_STRING_DATE_2))
                .build();
        ProductEntity createdProductEntity = ProductEntity.builder()
                .id(1001)
                .name("New product 1")
                .link("http://test/new-product-1")
                .category(category)
                .subcategory(subcategory)
                .price(100L)
                .store(store)
                .datePurchased(convertStrToLocalDateTime(MOCK_STRING_DATE_2))
                .build();

        // store
        when(storeRepository.findByName("SM Supermarket"))
                .thenReturn(Optional.empty());
        when(storeRepository.save(store)).thenReturn(store);
        // category
        when(categoryRepository.findByName("New Product Category"))
                .thenReturn(Optional.empty());
        when(categoryRepository.save(category)).thenReturn(category);
        // subcategory
        when(subcategoryRepository.save(subcategory)).thenReturn(subcategory);

        when(productRepository.save(productEntityToSave)).thenReturn(createdProductEntity);
        Product newProduct = productService.importProduct(productParam);

        //then
        Product expectedProduct = Product.builder()
                .id(1001)
                .name("New product 1")
                .link("http://test/new-product-1")
                .category("New Product Category")
                .subcategory("New Product Sub Category")
                .price(100L)
                .store("SM Supermarket")
                .datePurchased("05-13-2023")
                .build();
        assertEquals(expectedProduct, newProduct);
        verify(storeRepository).save(any(StoreEntity.class));
    }

    @Test
    @DisplayName("when add products and category does not exists, Then it should save the category")
    void importProductCategoryNoSave() {
        // given
        ProductImport productParam = ProductImport.builder()
                .name("New product 1")
                .link("http://test/new-product-1")
                .category("New Product Category")
                .subcategory("New Product Sub Category")
                .price(100L)
                .store("SM Supermarket")
                .datePurchased("05-13-2023")
                .build();

        // when
        CategoryEntity category = CategoryEntity.builder()
                .name("New Product Category")
                .build();
        SubcategoryEntity subcategory = SubcategoryEntity.builder()
                .category(category)
                .name("New Product Sub Category")
                .build();
        StoreEntity storeEntity = StoreEntity.builder()
                .name("SM Supermarket")
                .build();
        ProductEntity productEntityToSave = ProductEntity.builder()
                .name("New product 1")
                .link("http://test/new-product-1")
                .category(category)
                .subcategory(subcategory)
                .price(100L)
                .store(storeEntity)
                .datePurchased(convertStrToLocalDateTime(MOCK_STRING_DATE_2))
                .build();
        ProductEntity createdProductEntity = ProductEntity.builder()
                .id(1001)
                .name("New product 1")
                .link("http://test/new-product-1")
                .category(category)
                .subcategory(subcategory)
                .price(100L)
                .store(storeEntity)
                .datePurchased(convertStrToLocalDateTime(MOCK_STRING_DATE_2))
                .build();

        when(storeRepository.findByName("SM Supermarket"))
                .thenReturn(Optional.of(storeEntity));
        when(categoryRepository.findByName("New Product Category"))
                .thenReturn(Optional.empty());
        when(categoryRepository.save(any(CategoryEntity.class)))
                .thenReturn(category);
        when(subcategoryRepository.save(any(SubcategoryEntity.class)))
                .thenReturn(subcategory);
        when(productRepository.save(productEntityToSave)).thenReturn(createdProductEntity);
        Product newProduct = productService.importProduct(productParam);

        //then
        Product expectedProduct = Product.builder()
                .id(1001)
                .name("New product 1")
                .link("http://test/new-product-1")
                .category("New Product Category")
                .subcategory("New Product Sub Category")
                .price(100L)
                .store("SM Supermarket")
                .datePurchased("05-13-2023")
                .build();
        assertEquals(expectedProduct, newProduct);
        verify(categoryRepository).save(any(CategoryEntity.class));
    }

    @Test
    @DisplayName("when categories and subcategories do not exists, Then it should save subcategories")
    void importProductSubcategoryNoSave() {
        // given
        ProductImport productParam = ProductImport.builder()
                .name("New product 1")
                .link("http://test/new-product-1")
                .category("New Product Category")
                .subcategory("New Product Sub Category")
                .price(100L)
                .store("SM Supermarket")
                .datePurchased("05-13-2023")
                .build();

        // when
        CategoryEntity category = CategoryEntity.builder()
                .name("New Product Category")
                .build();
        SubcategoryEntity subcategory = SubcategoryEntity.builder()
                .category(category)
                .name("New Product Sub Category")
                .build();
        StoreEntity storeEntity = StoreEntity.builder()
                .name("SM Supermarket")
                .build();
        ProductEntity productEntityToSave = ProductEntity.builder()
                .name("New product 1")
                .link("http://test/new-product-1")
                .category(category)
                .subcategory(subcategory)
                .price(100L)
                .store(storeEntity)
                .datePurchased(convertStrToLocalDateTime(MOCK_STRING_DATE_2))
                .build();
        ProductEntity createdProductEntity = ProductEntity.builder()
                .id(1001)
                .name("New product 1")
                .link("http://test/new-product-1")
                .category(category)
                .subcategory(subcategory)
                .price(100L)
                .store(storeEntity)
                .datePurchased(convertStrToLocalDateTime(MOCK_STRING_DATE_2))
                .build();

        when(storeRepository.findByName("SM Supermarket"))
                .thenReturn(Optional.of(storeEntity));
        when(categoryRepository.findByName("New Product Category"))
                .thenReturn(Optional.of(category));
        when(subcategoryRepository.save(any(SubcategoryEntity.class)))
                .thenReturn(subcategory);
        when(productRepository.save(productEntityToSave)).thenReturn(createdProductEntity);
        Product newProduct = productService.importProduct(productParam);

        //then
        Product expectedProduct = Product.builder()
                .id(1001)
                .name("New product 1")
                .link("http://test/new-product-1")
                .category("New Product Category")
                .subcategory("New Product Sub Category")
                .price(100L)
                .store("SM Supermarket")
                .datePurchased("05-13-2023")
                .build();
        assertEquals(expectedProduct, newProduct);
        verify(subcategoryRepository).save(any(SubcategoryEntity.class));
    }

    private Product getProduct1() {
        return Product.builder()
                .id(1)
                .name("Product Name 1")
                .category("Category")
                .link("http://link1")
                .price(100L)
                .subcategory("Sub Category")
                .store("SM Supermarket")
                .datePurchased("04-21-2023")
                .purchaseHistoryList(singletonList(PurchaseHistory.builder()
                        .id(1)
                        .store("SM Supermarket")
                        .datePurchased(MOCK_STRING_DATE_1)
                        .link("http://link1")
                        .price(100L)
                        .build()))
                .build();
    }

    private Product getProduct2() {
        return Product.builder()
                .id(2)
                .name("Product Name 2")
                .category("Category")
                .link("http://link2")
                .price(150L)
                .subcategory("Sub Category")
                .store("Shopwise")
                .datePurchased("05-13-2023")
                .purchaseHistoryList(singletonList(PurchaseHistory.builder()
                        .id(2)
                        .store("Shopwise")
                        .datePurchased(MOCK_STRING_DATE_2)
                        .link("http://link2")
                        .price(150L)
                        .build()))
                .build();
    }

    private List<Product> getMockListOfProducts() {
        Product product1 = getProduct1();
        Product product2 = getProduct2();
        return asList(product1, product2);
    }

    private List<ProductEntity> getMockProductEntities() {
        CategoryEntity category = CategoryEntity.builder()
                .name("Category")
                .build();
        SubcategoryEntity subcategory = SubcategoryEntity.builder()
                .category(category)
                .name("Sub Category")
                .build();
        StoreEntity store1 = StoreEntity.builder()
                .name("SM Supermarket")
                .build();
        StoreEntity store2 = StoreEntity.builder()
                .name("Shopwise")
                .build();
        return asList(
                ProductEntity.builder()
                        .id(1)
                        .name("Product Name 1")
                        .category(category)
                        .link("http://link1")
                        .price(100L)
                        .subcategory(subcategory)
                        .store(store1)
                        .datePurchased(convertStrToLocalDateTime(MOCK_STRING_DATE_1))
                        .histories(singletonList(PurchaseHistoryEntity.builder()
                                .product(ProductEntity.builder().build())
                                .id(1)
                                .store(store1)
                                .datePurchased(convertStrToLocalDateTime(MOCK_STRING_DATE_1))
                                .link("http://link1")
                                .price(100L)
                                .build()))
                        .build(),
                ProductEntity.builder()
                        .id(2)
                        .name("Product Name 2")
                        .category(category)
                        .link("http://link2")
                        .price(150L)
                        .subcategory(subcategory)
                        .store(store2)
                        .datePurchased(convertStrToLocalDateTime(MOCK_STRING_DATE_2))
                        .histories(asList(PurchaseHistoryEntity.builder()
                                .product(ProductEntity.builder().build())
                                .id(2)
                                .store(store2)
                                .datePurchased(convertStrToLocalDateTime(MOCK_STRING_DATE_2))
                                .link("http://link2")
                                .price(150L)
                                .build()))
                        .build());
    }
}