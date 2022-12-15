package com.example.ecommerce.mvc.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class ProductReport {
    @Id
    private Integer id;
    private String productName;
    private String images;
    private Integer numberOfSale;
    private Integer totalMoney;
}
