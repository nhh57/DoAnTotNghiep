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



    ProductHelper productHelper = new ProductHelper();

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
        return productHelper.getListProductDataModel(productRepo.findByPrice(minPrice, maxPrice));
    }

    @Override
    public boolean existsById(Integer id) {
        return productRepo.existsById(id);
    }

    @Override
    public ProductDataModel save(ProductDataModel productDataModel) {
        Product productSaved=productRepo.save(productHelper.getProduct(productDataModel));
        return productHelper.getProductDataModel(productSaved);
    }

//    @Override
//    public ProductDataModel insert(ProductDataModel productDataModel) {
//        return productHelper.getProductDataModel(productRepo.insert(productDataModel.getProductName(), productDataModel.getPrice(),
//                productDataModel.getDiscount(),productDataModel.getNote(), productDataModel.getImages(),
//                productDataModel.getNumberOfSale(),productDataModel.getCategoryId(), productDataModel.getBrandId(), false));
//    }


    @Override
    public List<Product> findByBestSellingProducts(Integer numberOfProduct) {
        return productRepo.findByBestSellingProducts(numberOfProduct);
    }

//    @Override
//    public Product findByProductName(String productName) throws Exception {
//        return productRepo.findByProductName(productName);
//    }
//
//    @Override
//    public void createProductDataModel(ProductDataModelCreate product) {
//        productDataModelRepo.createProduct(product.getProductName(), product.getPrice(), product.getDiscount(),
//                product.getNote(), product.getImages(), product.getNumberOfSale(), product.getCategory(), product.getBrand());
//    }


}
