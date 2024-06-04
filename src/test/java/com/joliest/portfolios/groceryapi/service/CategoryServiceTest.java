package com.joliest.portfolios.groceryapi.service;

import com.joliest.portfolios.groceryapi.domain.entity.CategoryEntity;
import com.joliest.portfolios.groceryapi.domain.entity.SubcategoryEntity;
import com.joliest.portfolios.groceryapi.domain.repository.CategoryRepository;
import com.joliest.portfolios.groceryapi.model.Category;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.verify;
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
        List<CategoryEntity> categoriesFromDb = Collections.singletonList(CategoryEntity.builder()
                .id(1)
                .name("Category 1")
                .description("Desc 1")
                .subcategories(List.of(SubcategoryEntity.builder()
                        .name("Subcategory 1")
                        .build()))
                .build());
        when(categoryRepository.findAll()).thenReturn(categoriesFromDb);

        //then
        List<Category> expected = Collections.singletonList(Category.builder()
                .id(1)
                .name("Category 1")
                .description("Desc 1")
                .subcategories(List.of("Subcategory 1"))
                .build());
        List<Category> actual = categoryService.getCategories();
        assertEquals(expected, actual);
    }

    @Test
    void addMultipleCategories() {
        // given
        List<Category> requestBody = Collections.singletonList(Category.builder()
                .id(1)
                .name("Category 1")
                .description("Desc 1")
                .build());
        // when
        List<CategoryEntity> savedCategory = Collections.singletonList(CategoryEntity.builder()
                .id(1)
                .name("Category 1")
                .description("Desc 1")
                .build());
        when(categoryRepository.saveAll(anyList())).thenReturn(savedCategory);

        // then
        List<Category> expected = Collections.singletonList(Category.builder()
                .id(1)
                .name("Category 1")
                .description("Desc 1")
                .build());
        List<Category> actual = categoryService.addMultipleCategories(requestBody);
        assertEquals(expected, actual);
    }

    @Test
    void deleteCategoryById() {
        // given
        Integer categoryId = 1;

        // when
        categoryService.deleteCategoryById(categoryId);

        // then
        verify(categoryRepository).deleteById(categoryId);
    }

}