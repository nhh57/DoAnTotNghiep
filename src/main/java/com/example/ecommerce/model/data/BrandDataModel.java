package com.example.ecommerce.model.data;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Basic;
import javax.persistence.Column;
@Getter
@Setter
public class BrandDataModel {
    private int id;
    private String brandName;
    private Boolean isDeleted;
}
