package com.joliest.portfolios.groceryapi.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@SequenceGenerator(name="SEQ_GROCERY_ITEM", sequenceName = "grocery_item_id_seq", allocationSize = 1)
@Table(name = "grocery_item")
public class GroceryItemEntity {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_GROCERY_ITEM")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private ProductEntity product;

    @ManyToOne
    @JoinColumn(name = "grocery_id")
    private GroceryEntity grocery;

    private Float quantity;
    private String notes;
    private Long actualPrice;

//    TODO:. make this vv boolean
    private Long estimatedPrice;
}
