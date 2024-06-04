package com.joliest.portfolios.groceryapi.service;

import com.joliest.portfolios.groceryapi.domain.entity.CategoryEntity;
import com.joliest.portfolios.groceryapi.domain.entity.SubcategoryEntity;
import com.joliest.portfolios.groceryapi.domain.repository.CategoryRepository;
import com.joliest.portfolios.groceryapi.model.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;
    public List<Category> getCategories() {
        List<CategoryEntity> categoryEntities = categoryRepository.findAll();
        return categoryEntities.stream()
                .map(categoryEntity -> Category.builder()
                        .id(categoryEntity.getId())
                        .name(categoryEntity.getName())
                        .description(categoryEntity.getDescription())
                        .subcategories(categoryEntity.getSubcategories().stream()
                                .map(SubcategoryEntity::getName)
                                .collect(Collectors.toList()))
                        .build())
                .collect(Collectors.toList());
    }

    public List<Category> addMultipleCategories(List<Category> categories) {
        List<CategoryEntity> categoryEntities = categories.stream()
                .map(category -> CategoryEntity.builder()
                        .name(category.getName())
                        .description(category.getDescription())
                        .build())
                .collect(Collectors.toList());
        List<CategoryEntity> savedCategories = categoryRepository.saveAll(categoryEntities);
        return savedCategories.stream().map(categoryEntity -> Category.builder()
                        .id(categoryEntity.getId())
                        .name(categoryEntity.getName())
                        .description(categoryEntity.getDescription())
                        .build())
                .collect(Collectors.toList());
    }

    public void deleteCategoryById(Integer categoryId) {
        categoryRepository.deleteById(categoryId);
    }
}
