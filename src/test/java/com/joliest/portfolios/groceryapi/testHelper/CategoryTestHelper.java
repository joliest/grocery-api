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
    public static final Integer CATEGORY_TEST_ID_1 = 1;
    public static final String MOCK_CATEGORY_NAME_1 = "Category 1";
    @Autowired
    private CategoryRepository categoryRepository;

    public CategoryEntity setupCategory(String categoryName) {
        CategoryEntity categoryEntity = CategoryEntity.builder()
                .name(categoryName)
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
