package com.joliest.portfolios.groceryapi.domain.repository;

import com.joliest.portfolios.groceryapi.domain.entity.SubcategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface SubcategoryRepository extends JpaRepository<SubcategoryEntity, Integer> {
}
