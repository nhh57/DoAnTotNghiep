package com.example.ecommerce.service.impl;

import com.example.ecommerce.model.ProductImage;
import com.example.ecommerce.model.data.ProductImageDataModel;
import com.example.ecommerce.model.helper.ProductImageHelper;
import com.example.ecommerce.repository.ProductImageRepo;
import com.example.ecommerce.service.ProductImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductImageServiceImpl implements ProductImageService {
    @Autowired
    ProductImageRepo productImageRepo;

    ProductImageHelper productImageHelper=new ProductImageHelper();

    @Override
    public List<ProductImageDataModel> findByProductId(Integer productId) {
        return productImageHelper.getListProductImageDataModel(productImageRepo.findByProductId(productId));
    }

    @Override
    public Boolean save(Integer productId,String[] images) {
        try{
            for(int i=0;i<images.length;i++){
                ProductImage productImage=new ProductImage();
                productImage.setProductImageName(images[i]);
                productImage.setProductId(productId);
                productImageRepo.save(productImage);
            }
            return true;
        }catch (Exception e) {
            return false;
        }
    }
}
