package com.example.ecommerce.model.data;

import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;

@Getter
@Setter
public class ProductDataModel{
    private int id;
    private String productName;
    private Integer price;
    private Integer discount;
    private Double promotionPrice;
    private String note;
    private String image;
    private String[] images;
    private Integer categoryId;
    private Integer numberOfSale;
    private Integer brandId;
    private Boolean isDeleted;
    private CategoriesDataModel categories;
    private BrandDataModel brand;
}
