package com.joliest.portfolios.groceryapi.controller;

import com.joliest.portfolios.groceryapi.model.Category;
import com.joliest.portfolios.groceryapi.service.CategoryService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.annotation.Description;

import java.util.List;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CategoryControllerTest {
    @Mock
    private CategoryService categoryService;
    @InjectMocks
    private CategoryController categoryController;

    @Test
    @Description("when get categories is called, it should return list of categories")
    public void getCategory() {
        // when
        List<Category> expected = getMockCategories();
        when(categoryService.getCategories()).thenReturn(expected);
        List<Category> actual = categoryController.getCategories();

        // then
        assertEquals(expected, actual);
    }

    @Test
    @Description("when post categories is called, it should save the given categories")
    public void postCategory() {
        // given
        List<Category> requestBody = asList(Category.builder()
                .name("Category 1")
                .build());
        List<Category> responseBody = asList(Category.builder()
                .name("Category 1")
                .build());
        // when
        when(categoryService.addMultipleCategories(requestBody)).thenReturn(responseBody);
        List<Category> actual = categoryController.addMultipleCategories(requestBody);

        // then
        List<Category> expected = asList(Category.builder()
                .name("Category 1")
                .build());
        assertEquals(expected, actual);
    }

    private List<Category> getMockCategories() {
        return asList(
                Category.builder()
                        .id(1)
                        .name("Category 1")
                        .build(),
                Category.builder()
                        .id(2)
                        .name("Category 2")
                        .build());
    }
}