package com.example.ecommerce.service;

import com.example.ecommerce.model.Categories;
import com.example.ecommerce.model.data.CategoriesDataModel;

import java.util.List;

public interface CategoriesService {
    List<CategoriesDataModel> findAll(Integer soTrang,Integer soSanPham);
    List<CategoriesDataModel> findAllAdmin(Integer soTrang,Integer soSanPham);

    Boolean existsById(Integer id);
    CategoriesDataModel save(CategoriesDataModel categoriesDataModel);

    Boolean delete(Integer id);
}
