package com.example.ecommerce.service.impl;

import com.example.ecommerce.model.Product;
import com.example.ecommerce.model.data.ProductDataModel;
import com.example.ecommerce.model.helper.ProductHelper;
import com.example.ecommerce.repository.ProductRepo;
import com.example.ecommerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepo productRepo;
    ProductHelper productHelper=new ProductHelper();
    @Override
    public List<ProductDataModel> findAll() {
        return productHelper.getListProductDataModel(productRepo.findAll());
    }


    @Override
    public List<ProductDataModel> findProductExist() {
        return productHelper.getListProductDataModel(productRepo.findProductExist());
    }

    @Override
    public ProductDataModel findById(Integer id) {
        return productHelper.getProductDataModel(productRepo.findById(id).get());
    }

    @Override
    public List<ProductDataModel> findByName(String name) {
        return productHelper.getListProductDataModel(productRepo.findByName(name));
    }

    @Override
    public List<ProductDataModel> findByPrice(BigDecimal minPrice, BigDecimal maxPrice) {
        return productHelper.getListProductDataModel(productRepo.findByPrice(minPrice,maxPrice));
    }

    @Override
    public boolean existsById(Integer id) {
        return productRepo.existsById(id);
    }

    @Override
    public Product save(ProductDataModel productDataModel) {
        return productRepo.save(productHelper.getProduct(productDataModel));
    }

    @Override
    public List<Product> findByBestSellingProducts(Integer numberOfProduct) {
        return productRepo.findByBestSellingProducts(numberOfProduct);
    }


}
