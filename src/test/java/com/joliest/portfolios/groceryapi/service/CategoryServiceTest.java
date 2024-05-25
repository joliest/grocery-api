package com.joliest.portfolios.groceryapi.service;

import com.joliest.portfolios.groceryapi.domain.entity.CategoryEntity;
import com.joliest.portfolios.groceryapi.domain.repository.CategoryRepository;
import com.joliest.portfolios.groceryapi.model.Category;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {
    @Mock
    private CategoryRepository categoryRepository;
    @InjectMocks
    private CategoryService categoryService;

    @Test
    void getCategories() {
        // when
        List<CategoryEntity> categoriesFromDb = asList(CategoryEntity.builder()
                .id(1)
                .name("Category 1")
                .description("Desc 1")
                .build());
        when(categoryRepository.findAll()).thenReturn(categoriesFromDb);

        //then
        List<Category> expected = asList(Category.builder()
                .id(1)
                .name("Category 1")
                .description("Desc 1")
                .build());
        List<Category> actual = categoryService.getCategories();
        assertEquals(expected, actual);
    }

    @Test
    void addMultipleCategories() {
        // given
        List<Category> requestBody = asList(Category.builder()
                .id(1)
                .name("Category 1")
                .description("Desc 1")
                .build());
        // when
        List<CategoryEntity> savedCategory = asList(CategoryEntity.builder()
                .id(1)
                .name("Category 1")
                .description("Desc 1")
                .build());
        when(categoryRepository.saveAll(anyList())).thenReturn(savedCategory);

        //then
        List<Category> expected = asList(Category.builder()
                .id(1)
                .name("Category 1")
                .description("Desc 1")
                .build());
        List<Category> actual = categoryService.addMultipleCategories(requestBody);
        assertEquals(expected, actual);
    }

}