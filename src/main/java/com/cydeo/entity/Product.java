package com.cydeo.entity;

import com.cydeo.enums.ProductUnit;
import lombok.*;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "products")
@Where(clause = "is_deleted=false")
public class Product extends BaseEntity {

    private String name;
    private int quantityInStock;
    private int lowLimitAlert;
    @Enumerated(EnumType.STRING)
    private ProductUnit productUnit;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

}
