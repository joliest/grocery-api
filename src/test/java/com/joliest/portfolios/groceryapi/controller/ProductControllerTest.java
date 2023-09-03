package com.joliest.portfolios.groceryapi.controller;

import com.joliest.portfolios.groceryapi.model.Product;
import com.joliest.portfolios.groceryapi.service.ProductService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.annotation.Description;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductControllerTest {
    @Mock
    private ProductService productService;
    @InjectMocks
    private ProductController productController;

    @Test
    @Description("when get products is called, it should return list of products")
    public void getProducts() {
        // when
        List<Product> expected = getMockProducts();
        when(productService.getProducts()).thenReturn(expected);
        List<Product> actual = productController.getProducts();

        // then
        assertEquals(expected, actual);
    }

    private List<Product> getMockProducts() {
        return Arrays.asList(
                Product.builder()
                        .id(1)
                        .name("Product 1")
                        .build(),
                Product.builder()
                        .id(2)
                        .name("Product 2")
                        .build());
    }
}