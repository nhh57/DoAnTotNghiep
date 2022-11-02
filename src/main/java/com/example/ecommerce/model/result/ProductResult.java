package com.example.ecommerce.model.result;

import com.example.ecommerce.model.data.ProductDataModel;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ProductResult {
    Integer totalPage;
    List<ProductDataModel> data;
}
