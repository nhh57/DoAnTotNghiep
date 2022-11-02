package com.example.ecommerce.model.helper;

import com.example.ecommerce.model.ProductImage;
import com.example.ecommerce.model.data.ProductImageDataModel;

import java.util.ArrayList;
import java.util.List;

public class ProductImageHelper {
    public ProductImage getProductImage(ProductImageDataModel productImageDataModel){
        ProductImage productImage=new ProductImage();
        productImage.setProductId(productImageDataModel.getProductId());
        productImage.setProductImageName(productImageDataModel.getProductImageName());
        productImage.setDeleted(false);
        return productImage;
    }
    public ProductImageDataModel getProductImageDataModel(ProductImage productImage){
        ProductImageDataModel productImageDataModel=new ProductImageDataModel();
        productImageDataModel.setProductId(productImage.getProductId());
        productImageDataModel.setProductImageName(productImage.getProductImageName());
        return productImageDataModel;
    }
    public List<ProductImageDataModel> getListProductImageDataModel(List<ProductImage> listProductImage){
        List<ProductImageDataModel> list=new ArrayList<>();
        for(ProductImage productImage:listProductImage){
            list.add(getProductImageDataModel(productImage));
        }
        return list;
    }
}
