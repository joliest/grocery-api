package com.joliest.portfolios.groceryapi.domain.repository;

import com.joliest.portfolios.groceryapi.domain.entity.CategoryEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<CategoryEntity, Integer> {
    Optional<CategoryEntity> findByName(String name);
    @Transactional
    void removeByName(String name);
}
