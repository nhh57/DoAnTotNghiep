package com.example.ecommerce.model.data;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Basic;
import javax.persistence.Column;

@Getter
@Setter
public class CategoriesDataModel {
    private int id;
    private String categoryName;
    private Boolean isDeleted;
}
