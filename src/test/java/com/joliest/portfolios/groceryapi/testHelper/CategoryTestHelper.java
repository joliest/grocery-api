package com.joliest.portfolios.groceryapi.testHelper;

import com.joliest.portfolios.groceryapi.domain.entity.CategoryEntity;
import com.joliest.portfolios.groceryapi.domain.repository.CategoryRepository;
import com.joliest.portfolios.groceryapi.model.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.Arrays.asList;

@Service
public class CategoryTestHelper {
    @Autowired
    private CategoryRepository categoryRepository;

    public CategoryEntity setupCategory(String categoryName) {
        String description = String.format("%s-description", categoryName);
        CategoryEntity categoryEntity = CategoryEntity.builder()
                .name(categoryName)
                .description(description)
                .build();
        return categoryRepository.save(categoryEntity);
    }

    public List<Category> createRequestBody() {
        Category category1 = Category.builder()
                .name("Category Request 1")
                .description("Desc Request 1")
                .build();
        Category category2 = Category.builder()
                .name("Category Request 2")
                .description("Desc Request 2")
                .build();
        return asList(category1, category2);
    }
}
