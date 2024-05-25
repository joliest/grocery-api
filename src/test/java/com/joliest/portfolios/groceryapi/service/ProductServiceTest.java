package com.joliest.portfolios.groceryapi.service;

import com.joliest.portfolios.groceryapi.domain.entity.ProductEntity;
import com.joliest.portfolios.groceryapi.domain.entity.StoreEntity;
import com.joliest.portfolios.groceryapi.domain.repository.ProductRepository;
import com.joliest.portfolios.groceryapi.domain.repository.StoreRepository;
import com.joliest.portfolios.groceryapi.model.Product;
import com.joliest.portfolios.groceryapi.model.Products;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.joliest.portfolios.groceryapi.utils.DateUtil.convertStrToLocalDateTime;
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
    void addProduct() {
        // given
        Product productParam = Product.builder()
                .name("New product 1")
                .link("http://test/new-product-1")
                .category("New Product Category")
                .subcategory("New Product Sub Category")
                .price(100L)
                .store("SM Supermarket")
                .datePurchased("05-13-2023")
                .build();

        // when
        ProductEntity productEntityToSave = ProductEntity.builder()
                .name("New product 1")
                .link("http://test/new-product-1")
                .category("New Product Category")
                .subcategory("New Product Sub Category")
                .price(100L)
                .store(StoreEntity.builder()
                        .name("SM Supermarket").build())
                .datePurchased(convertStrToLocalDateTime(MOCK_STRING_DATE_2))
                .build();
        ProductEntity createdProductEntity = ProductEntity.builder()
                .id(1001)
                .name("New product 1")
                .link("http://test/new-product-1")
                .category("New Product Category")
                .subcategory("New Product Sub Category")
                .price(100L)
                .store(StoreEntity.builder()
                        .name("SM Supermarket").build())
                .datePurchased(convertStrToLocalDateTime(MOCK_STRING_DATE_2))
                .build();

        when(storeRepository.findByName("SM Supermarket"))
                .thenReturn(Optional.of(StoreEntity.builder()
                        .name("SM Supermarket")
                        .build()));
        when(productRepository.save(productEntityToSave)).thenReturn(createdProductEntity);
        Product newProduct = productService.addProduct(productParam);

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
    }

    @Test
    @DisplayName("when adding of multiple products, Then it should add the multiple products")
    void addMultipleProducts() {
        // given
        Products products = Products.builder()
                .products(Arrays.asList(
                        Product.builder()
                                .name("Product Name 1")
                                .category("Category")
                                .link("http://link1")
                                .price(100L)
                                .subcategory("Sub Category")
                                .store("SM Supermarket")
                                .datePurchased("04-21-2023")
                                .build(),
                        Product.builder()
                                .name("Product Name 2")
                                .category("Category")
                                .link("http://link2")
                                .price(150L)
                                .subcategory("Sub Category")
                                .store("Shopwise")
                                .datePurchased("05-13-2023")
                                .build())
                ).build();

        // when
        when(storeRepository.findByName("SM Supermarket" ))
                .thenReturn(Optional.of(StoreEntity.builder()
                        .name("SM Supermarket")
                        .build()));
        when(productRepository.save(ProductEntity.builder()
                .name("Product Name 1")
                .category("Category")
                .link("http://link1")
                .price(100L)
                .subcategory("Sub Category")
                .store(StoreEntity.builder()
                        .name("SM Supermarket").build())
                .datePurchased(convertStrToLocalDateTime(MOCK_STRING_DATE_1))
                .build()
        )).thenReturn(ProductEntity.builder()
                .id(1)
                .name("Product Name 1")
                .category("Category")
                .link("http://link1")
                .price(100L)
                .subcategory("Sub Category")
                .store(StoreEntity.builder()
                        .name("SM Supermarket").build())
                .datePurchased(convertStrToLocalDateTime(MOCK_STRING_DATE_1))
                .build());
        when(storeRepository.findByName("Shopwise"))
                .thenReturn(Optional.of(StoreEntity.builder()
                        .name("Shopwise")
                        .build()));
        when(productRepository.save(ProductEntity.builder()
                .name("Product Name 2")
                .category("Category")
                .link("http://link2")
                .price(150L)
                .subcategory("Sub Category")
                .store(StoreEntity.builder()
                        .name("Shopwise").build())
                .datePurchased(convertStrToLocalDateTime(MOCK_STRING_DATE_2))
                .build()
        )).thenReturn(ProductEntity.builder()
                .id(2)
                .name("Product Name 2")
                .category("Category")
                .link("http://link2")
                .price(150L)
                .subcategory("Sub Category")
                .store(StoreEntity.builder()
                        .name("Shopwise").build())
                .datePurchased(convertStrToLocalDateTime(MOCK_STRING_DATE_2))
                .build());
        List<Product> expected = Arrays.asList(Product.builder()
                .id(1)
                .name("Product Name 1")
                .category("Category")
                .link("http://link1")
                .price(100L)
                .subcategory("Sub Category")
                .store("SM Supermarket")
                .datePurchased("04-21-2023")
                .build(), Product.builder()
                .id(2)
                .name("Product Name 2")
                .category("Category")
                .link("http://link2")
                .price(150L)
                .subcategory("Sub Category")
                .store("Shopwise")
                .datePurchased("05-13-2023")
                .build());
        List<Product> actual = productService.addMultipleProducts(products);

        // then
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("when given store is empty, Then it should save the new store")
    void addProductStoreIsEmpty() {
        // given
        Product productParam = Product.builder()
                .name("New product 1")
                .link("http://test/new-product-1")
                .category("New Product Category")
                .subcategory("New Product Sub Category")
                .price(100L)
                .store("SM Supermarket")
                .datePurchased("05-13-2023")
                .build();

        // when
        ProductEntity productEntityToSave = ProductEntity.builder()
                .name("New product 1")
                .link("http://test/new-product-1")
                .category("New Product Category")
                .subcategory("New Product Sub Category")
                .price(100L)
                .store(StoreEntity.builder()
                        .name("SM Supermarket").build())
                .datePurchased(convertStrToLocalDateTime(MOCK_STRING_DATE_2))
                .build();
        ProductEntity createdProductEntity = ProductEntity.builder()
                .id(1001)
                .name("New product 1")
                .link("http://test/new-product-1")
                .category("New Product Category")
                .subcategory("New Product Sub Category")
                .price(100L)
                .store(StoreEntity.builder()
                        .name("SM Supermarket").build())
                .datePurchased(convertStrToLocalDateTime(MOCK_STRING_DATE_2))
                .build();

        when(storeRepository.findByName("SM Supermarket"))
                .thenReturn(Optional.empty());
        when(storeRepository.save(StoreEntity.builder().name("SM Supermarket").build()))
                .thenReturn(StoreEntity.builder().name("SM Supermarket").build());
        when(productRepository.save(productEntityToSave)).thenReturn(createdProductEntity);
        Product newProduct = productService.addProduct(productParam);

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
                .build();
    }

    private List<Product> getMockListOfProducts() {
        Product product1 = getProduct1();
        Product product2 = getProduct2();
        return Arrays.asList(product1, product2);
    }

    private List<ProductEntity> getMockProductEntities() {
        return Arrays.asList(
                ProductEntity.builder()
                        .id(1)
                        .name("Product Name 1")
                        .category("Category")
                        .link("http://link1")
                        .price(100L)
                        .subcategory("Sub Category")
                        .store(StoreEntity.builder()
                                .name("SM Supermarket").build())
                        .datePurchased(convertStrToLocalDateTime(MOCK_STRING_DATE_1))
                        .build(),
                ProductEntity.builder()
                        .id(2)
                        .name("Product Name 2")
                        .category("Category")
                        .link("http://link2")
                        .price(150L)
                        .subcategory("Sub Category")
                        .store(StoreEntity.builder()
                                .name("Shopwise").build())
                        .datePurchased(convertStrToLocalDateTime(MOCK_STRING_DATE_2))
                        .build());
    }
}