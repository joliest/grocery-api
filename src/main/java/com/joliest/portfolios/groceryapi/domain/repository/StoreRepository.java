package com.joliest.portfolios.groceryapi.domain.repository;

import com.joliest.portfolios.groceryapi.domain.entity.StoreEntity;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StoreRepository extends JpaRepository<StoreEntity, Integer> {
    Optional<StoreEntity> findByName(String name);
    boolean existsById(Integer id);
    @Transactional
    void removeByName(String name);
}
