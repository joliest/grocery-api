package com.joliest.portfolios.groceryapi.domain.repository;

import com.joliest.portfolios.groceryapi.domain.entity.CategoryEntity;
import com.joliest.portfolios.groceryapi.domain.entity.SubcategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface SubcategoryRepository extends JpaRepository<SubcategoryEntity, Integer> {
    Optional<SubcategoryEntity> findByNameAndCategory(String name, CategoryEntity category);
}
