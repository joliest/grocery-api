package com.joliest.portfolios.groceryapi.domain.repository;

import com.joliest.portfolios.groceryapi.domain.entity.ComponentProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ComponentProductRepository extends JpaRepository<ComponentProductEntity, Integer> {
}
