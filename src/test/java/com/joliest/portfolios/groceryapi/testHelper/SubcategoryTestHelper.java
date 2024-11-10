package com.joliest.portfolios.groceryapi.testHelper;

import com.joliest.portfolios.groceryapi.domain.entity.CategoryEntity;
import com.joliest.portfolios.groceryapi.domain.entity.SubcategoryEntity;
import com.joliest.portfolios.groceryapi.domain.repository.CategoryRepository;
import com.joliest.portfolios.groceryapi.domain.repository.SubcategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SubcategoryTestHelper {
    @Autowired
    private SubcategoryRepository subcategoryRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    public SubcategoryEntity setupSubcategoryWithCategoryId(Integer categoryId) {
        CategoryEntity categoryEntity = categoryRepository.findById(categoryId).get();
        SubcategoryEntity subcategory = SubcategoryEntity.builder()
                .category(categoryEntity)
                .name("Subcategory 1")
                .description("Desc 1")
                .build();
        return subcategoryRepository.save(subcategory);
    }
}
