package com.joliest.portfolios.groceryapi.domain.repository;

import com.joliest.portfolios.groceryapi.domain.entity.GroceryItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroceryItemRepository extends JpaRepository<GroceryItemEntity, Integer> {
}
