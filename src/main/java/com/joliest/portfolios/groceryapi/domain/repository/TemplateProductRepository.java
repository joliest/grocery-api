package com.joliest.portfolios.groceryapi.domain.repository;

import com.joliest.portfolios.groceryapi.domain.entity.TemplateProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TemplateProductRepository extends JpaRepository<TemplateProductEntity, Integer> {
}
