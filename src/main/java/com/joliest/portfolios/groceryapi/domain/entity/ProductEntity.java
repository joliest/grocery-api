package com.joliest.portfolios.groceryapi.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.GenerationType;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.GeneratedValue;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@SequenceGenerator(name="SEQ_PRODUCT", sequenceName = "product_id_seq", allocationSize = 1)
@Table(name = "product")
public class ProductEntity {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_PRODUCT")
    private Integer id;
    private String name;
    private Long price;
    private String store;
    private String category;
    private String subCategory;
    private String link;
    private LocalDateTime datePurchased;
}
