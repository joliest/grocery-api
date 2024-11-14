package com.joliest.portfolios.groceryapi.testHelper;

import com.joliest.portfolios.groceryapi.domain.entity.CategoryEntity;
import com.joliest.portfolios.groceryapi.domain.entity.SubcategoryEntity;
import com.joliest.portfolios.groceryapi.domain.repository.SubcategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SubcategoryTestHelper {
    @Autowired
    private SubcategoryRepository subcategoryRepository;

    public SubcategoryEntity setupSubcategoryWithCategory(String subcategoryName, Integer categoryId) {
        SubcategoryEntity subcategoryEntity = SubcategoryEntity.builder()
                .category(CategoryEntity.builder()
                        .id(categoryId)
                        .build())
                .name(subcategoryName)
                .build();
        return subcategoryRepository.save(subcategoryEntity);
    }
}
