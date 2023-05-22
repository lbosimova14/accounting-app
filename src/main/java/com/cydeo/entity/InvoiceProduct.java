package com.cydeo.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "invoice_products")
@NoArgsConstructor
@Getter
@Setter
public class InvoiceProduct extends BaseEntity {

    private int quantity;

    private BigDecimal price;

    private int tax;

    private BigDecimal profitLoss;

    @Column(name = "remaining_quantity")
    private int remainingQty;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "invoice_id")
    private Invoice invoice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

}
