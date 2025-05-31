package com.joliest.portfolios.groceryapi.domain.repository;

import com.joliest.portfolios.groceryapi.domain.entity.ProductEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<ProductEntity, Integer> {
//    TODO:. when product history is available, upated this to findBy
    Optional<ProductEntity> findFirstByNameAndCategoryNameAndSubcategoryName(String name, String categoryName, String subcategoryName);
    Slice<ProductEntity> searchAllByNameContainingIgnoreCase(String name, Pageable pageable);
}
