package com.example.ecommerce.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "sale_detail")
public class SaleDetail {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private int id;

    @Column(name = "sale_id", nullable = true)
    private Integer saleId;

    @Column(name = "product_id", nullable = true)
    private Integer productId;

    @Column(name = "discount_old", nullable = true)
    private Integer discountOld;

    @Column(name = "discount_sale", nullable = true)
    private Integer discountSale;

    @ManyToOne
    @JoinColumn(name = "sale_id", referencedColumnName = "id",insertable = false, updatable = false)
    private Sale sale;

    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id",insertable = false, updatable = false)
    private Product product;
}
