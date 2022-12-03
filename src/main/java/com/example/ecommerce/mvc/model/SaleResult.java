package com.example.ecommerce.mvc.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SaleResult {
    private int id;
    private String saleDateStart;
    private String saleTimeStart;
    private String saleDateEnd;
    private String saleTimeEnd;
    private Boolean isDeleted;
    private String saleName;
    private Boolean showProduct;
    private Boolean showSale;
    private String dateStartShow;
}
