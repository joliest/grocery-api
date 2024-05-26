package com.joliest.portfolios.groceryapi.domain.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Optional;

import static org.springframework.util.ObjectUtils.isEmpty;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@SequenceGenerator(name="SEQ_CATEGORY", sequenceName = "category_id_seq", allocationSize = 1)
@Table(name = "category")
public class CategoryEntity {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_CATEGORY")
    private Integer id;
    private String name;
    private String description;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "category_id")
    private List<SubcategoryEntity> subcategories;

    public Optional<SubcategoryEntity> findSubcategory(String givenSubcategory) {
        if (isEmpty(this.subcategories)) {
            return Optional.empty();
        }
        return this.subcategories.stream()
                .filter(subcategory -> givenSubcategory.equals(subcategory.getName()))
                .findFirst();
    }
}
