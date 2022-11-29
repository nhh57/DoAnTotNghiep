package com.example.ecommerce.model.helper;

import com.example.ecommerce.model.Brand;
import com.example.ecommerce.model.data.BrandDataModel;

import java.util.ArrayList;
import java.util.List;

public class BrandHelper {
    public BrandDataModel getBrandDataModel(Brand brand){
        BrandDataModel brandDataModel=new BrandDataModel();
        brandDataModel.setId(brand.getId());
        brandDataModel.setBrandName(brand.getBrandName());
        brandDataModel.setIsDeleted(brand.getDeleted());
        return brandDataModel;
    }
    public List<BrandDataModel> getListCategoriesDataModel(List<Brand> listBrand){
        List<BrandDataModel> list=new ArrayList<>();
        for(Brand brand:listBrand){
            list.add(getBrandDataModel(brand));
        }
        return list;
    }
    public Brand getBrand(BrandDataModel brandDataModel){
        Brand brand=new Brand();
        brand.setId(brandDataModel.getId());
        brand.setBrandName(brandDataModel.getBrandName());
        brand.setDeleted(brandDataModel.getIsDeleted());
        return brand;
    }
    public int getTotalPage(int soSanPham, List<Brand> list) {
        int tongSoSanPham = list.size();
        int tongSoTrang = 1;
        float tempFloat = (float) tongSoSanPham / soSanPham;
        int tempInt = (int) tempFloat;
        if (tempFloat - tempInt > 0) {
            tongSoTrang = tempInt + 1;
        } else {
            tongSoTrang = tempInt;
        }
        return tongSoTrang;
    }
}
