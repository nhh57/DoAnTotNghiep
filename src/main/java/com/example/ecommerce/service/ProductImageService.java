package com.example.ecommerce.service;

import com.example.ecommerce.model.ProductImage;
import com.example.ecommerce.model.data.ProductImageDataModel;

import java.util.List;

public interface ProductImageService {
    List<ProductImageDataModel> findByProductId(Integer productId);
    Boolean save(Integer productId,String[] images);
}
