package com.example.ecommerce.service;

import com.example.ecommerce.model.Product;
import com.example.ecommerce.model.data.ProductDataModel;
import com.example.ecommerce.model.data.ProductDataModelCreate;

import java.math.BigDecimal;
import java.util.List;

public interface ProductService {
    List<ProductDataModel> findAll(Integer soTrang,Integer soSanPham);
    List<ProductDataModel> findProductExist(Integer soTrang,Integer soSanPham);
    ProductDataModel findById(Integer id);
    List<ProductDataModel> findByName(String name);
    List<ProductDataModel> findByPrice(BigDecimal minPrice, BigDecimal maxPrice);
    boolean existsById(Integer id);
    ProductDataModel save(ProductDataModel productDataModel);

    //    ProductDataModel insert(ProductDataModel productDataModel);
    List<ProductDataModel> findByBestSellingProducts(Integer numberOfProduct);
//    Product findByProductName(String productName) throws Exception;
//
//    void createProductDataModel(ProductDataModelCreate productDataModelCreate);
}
