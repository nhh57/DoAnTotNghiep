package com.example.ecommerce.service;

import com.example.ecommerce.model.data.BrandDataModel;

import java.util.List;

public interface BrandService {
    List<BrandDataModel> findAll(Integer soTrang,Integer soSanPham);
    List<BrandDataModel> findAllAdmin(Integer soTrang,Integer soSanPham);

    Boolean existsById(Integer id);
    BrandDataModel save(BrandDataModel brandDataModel);
    Boolean delete(Integer id);
}
