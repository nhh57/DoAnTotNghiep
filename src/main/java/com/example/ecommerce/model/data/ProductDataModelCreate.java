package com.example.ecommerce.model.data;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.math.BigDecimal;
@Entity
@Getter
@Setter
public class ProductDataModelCreate implements Serializable {
    @Id
    private Integer id;
    private String productName;
    private BigDecimal price;
    private Double discount;
    private String note;
    private String images;
    private Integer numberOfSale;
    private String category;
    private String brand;
}
