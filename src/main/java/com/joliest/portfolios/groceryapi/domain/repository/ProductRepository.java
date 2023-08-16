package com.joliest.portfolios.groceryapi.domain.repository;

import com.joliest.portfolios.groceryapi.domain.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<ProductEntity, Integer> {
}
