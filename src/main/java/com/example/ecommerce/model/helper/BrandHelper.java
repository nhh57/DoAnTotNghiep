package com.example.ecommerce.model.helper;

import com.example.ecommerce.model.Brand;
import com.example.ecommerce.model.data.BrandDataModel;

public class BrandHelper {
    public BrandDataModel getBrandDataModel(Brand brand){
        BrandDataModel brandDataModel=new BrandDataModel();
        brandDataModel.setId(brand.getId());
        brandDataModel.setBrandName(brand.getBrandName());
        brandDataModel.setIsDeleted(brand.getDeleted());
        return brandDataModel;
    }
}
