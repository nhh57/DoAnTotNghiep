package com.example.ecommerce.service;

import com.example.ecommerce.model.Product;
import com.example.ecommerce.model.data.ProductDataModel;

import java.math.BigDecimal;
import java.util.List;

public interface ProductService {
    List<ProductDataModel> findAll();
    List<ProductDataModel> findProductExist();
    ProductDataModel findById(Integer id);
    List<ProductDataModel> findByName(String name);
    List<ProductDataModel> findByPrice(BigDecimal minPrice, BigDecimal maxPrice);
    boolean existsById(Integer id);
    Product save(ProductDataModel productDataModel);
    List<Product> findByBestSellingProducts(Integer numberOfProduct);
}
