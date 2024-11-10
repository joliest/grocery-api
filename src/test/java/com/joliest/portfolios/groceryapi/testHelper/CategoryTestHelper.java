package com.joliest.portfolios.groceryapi.testHelper;

import com.joliest.portfolios.groceryapi.domain.entity.CategoryEntity;
import com.joliest.portfolios.groceryapi.domain.repository.CategoryRepository;
import com.joliest.portfolios.groceryapi.model.Category;
import com.joliest.portfolios.groceryapi.model.Store;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;

@Service
public class CategoryTestHelper {
    public static final Integer CATEGORY_TEST_ID_1 = 1;
    public static final String MOCK_CATEGORY_NAME_1 = "Category 1";
    public static final String MOCK_CATEGORY_DESCRIPTION_1 = "Category Description 1";
    public static final String MOCK_CATEGORY_NAME_2 = "Category 2";
    public static final String MOCK_CATEGORY_DESCRIPTION_2 = "Category Description 2";
    @Autowired
    private CategoryRepository categoryRepository;

    public List<Integer> setupCategories() {
        CategoryEntity category1 = CategoryEntity.builder()
                // id = 1
                .name(MOCK_CATEGORY_NAME_1)
                .description(MOCK_CATEGORY_DESCRIPTION_1)
                .build();
        CategoryEntity category2 = CategoryEntity.builder()
                // id = 2
                .name(MOCK_CATEGORY_NAME_2)
                .description(MOCK_CATEGORY_DESCRIPTION_2)
                .build();
        List<CategoryEntity> categoriesToSave = asList(category1, category2);
        List<CategoryEntity> savedCategory = categoryRepository.saveAll(categoriesToSave);
        return savedCategory.stream()
                .map(CategoryEntity::getId)
                .collect(Collectors.toList());
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
