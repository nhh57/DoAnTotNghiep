package com.example.ecommerce.request;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ProductRequest {
    private String productName;
    private BigDecimal price;
    private Double discount;
    private String note;
    private String images;
    private Integer numberOfSale;
    private CategoryRequest category;
    private BrandRequest brand;
}
