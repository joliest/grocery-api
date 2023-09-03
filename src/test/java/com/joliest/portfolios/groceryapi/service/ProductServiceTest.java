package com.joliest.portfolios.groceryapi.service;

import com.joliest.portfolios.groceryapi.domain.entity.ProductEntity;
import com.joliest.portfolios.groceryapi.domain.repository.ProductRepository;
import com.joliest.portfolios.groceryapi.model.Product;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static com.joliest.portfolios.groceryapi.utils.DateUtil.convertStrTimestampToDate;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {
    @Mock
    private ProductRepository productRepository;
    @InjectMocks
    private ProductService productService;

    @Test
    @DisplayName("When get products is called, Then it'll return the products")
    void getProducts() {
        // when
        List<ProductEntity> productsStream = getMockProductEntities();
        when(productRepository.findAll()).thenReturn(productsStream);
        List<Product> actual = productService.getProducts();

        // then
        List<Product> expected = getExpectedProducts();
        assertEquals(expected, actual);
    }

    private List<Product> getExpectedProducts() {
        return Arrays.asList(
                Product.builder()
                        .id(1)
                        .name("Product Name 1")
                        .category("Category")
                        .link("http://link1")
                        .price(100L)
                        .subCategory("Sub Category")
                        .store("SM Supermarket")
                        .datePurchased("04-21-2023")
                        .build(),
                Product.builder()
                        .id(2)
                        .name("Product Name 2")
                        .category("Category")
                        .link("http://link2")
                        .price(150L)
                        .subCategory("Sub Category")
                        .store("Shopwise")
                        .datePurchased("05-13-2023")
                        .build()
        );
    }

    private List<ProductEntity> getMockProductEntities() {
        return Arrays.asList(
                ProductEntity.builder()
                        .id(1)
                        .name("Product Name 1")
                        .category("Category")
                        .link("http://link1")
                        .price(100L)
                        .subCategory("Sub Category")
                        .store("SM Supermarket")
                        .datePurchased(convertStrTimestampToDate("2023-04-21T00:00:00.000Z"))
                        .build(),
                ProductEntity.builder()
                        .id(2)
                        .name("Product Name 2")
                        .category("Category")
                        .link("http://link2")
                        .price(150L)
                        .subCategory("Sub Category")
                        .store("Shopwise")
                        .datePurchased(convertStrTimestampToDate("2023-05-13T00:00:00.000Z"))
                        .build());
    }
}