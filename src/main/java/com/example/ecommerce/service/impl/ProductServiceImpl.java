package com.example.ecommerce.service.impl;

import com.example.ecommerce.model.Product;
import com.example.ecommerce.model.data.ProductDataModel;
import com.example.ecommerce.model.data.ProductImageDataModel;
import com.example.ecommerce.model.helper.ProductHelper;
import com.example.ecommerce.repository.ProductRepo;
import com.example.ecommerce.service.ProductImageService;
import com.example.ecommerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {
    @Autowired(required = true)
    private ProductRepo productRepo;

    @Autowired
    private ProductImageService productImageService;
    ProductHelper productHelper = new ProductHelper();
    private String[] getImages(ProductDataModel productDataModel){
        List<ProductImageDataModel> list=productImageService.findByProductId(productDataModel.getId());
        String[] images=new String[list.size()];
        if(!list.isEmpty()){
            for(int i=0;i<list.size();i++){
                images[i]=list.get(i).getProductImageName();
            }
        }
        return images;
    }


    @Override
    public List<ProductDataModel> findAll(Integer soTrang,Integer soSanPham) {
        Pageable pageable = PageRequest.of(soTrang-1, soSanPham);
        Page<Product> pageProduct=productRepo.findAll(pageable);
        List<Product> list=pageProduct.getContent();
        List<ProductDataModel> listResult=productHelper.getListProductDataModel(list);
        for(ProductDataModel productDataModel:listResult){
            if (productDataModel != null) {
                productDataModel.setImages(getImages(productDataModel));
            }
        }
        return listResult;
    }

    @Override
    public List<ProductDataModel> findProductExist(Integer soTrang,Integer soSanPham) {
        Pageable pageable = PageRequest.of(soTrang-1, soSanPham);
        Page<Product> pageProduct=productRepo.findProductExist(pageable);
        List<Product> list=pageProduct.getContent();
        List<ProductDataModel> listResult=productHelper.getListProductDataModel(list);
        for(ProductDataModel productDataModel:listResult){
            if (productDataModel != null) {
                productDataModel.setImages(getImages(productDataModel));
            }
        }
        return listResult;
    }

    @Override
    public ProductDataModel findById(Integer id) {
        ProductDataModel productDataModel=productHelper.getProductDataModel(productRepo.findById(id).get());
        productDataModel.setImages(getImages(productDataModel));
        return productDataModel;
    }

    @Override
    public List<ProductDataModel> findByName(String name) {
        List<ProductDataModel> list=productHelper.getListProductDataModel(productRepo.findByName(name));
        for(ProductDataModel productDataModel:list){
            if (productDataModel != null) {
                productDataModel.setImages(getImages(productDataModel));
            }
        }
        return list;
    }

    @Override
    public List<ProductDataModel> findByPrice(BigDecimal minPrice, BigDecimal maxPrice) {
        List<ProductDataModel> list=productHelper.getListProductDataModel(productRepo.findByPrice(minPrice, maxPrice));
        for(ProductDataModel productDataModel:list){
            if (productDataModel != null) {
                productDataModel.setImages(getImages(productDataModel));
            }
        }
        return list;
    }

    @Override
    public boolean existsById(Integer id) {
        return productRepo.existsById(id);
    }

    @Override
    public ProductDataModel save(ProductDataModel productDataModel) {
        Product productSaved=productRepo.save(productHelper.getProduct(productDataModel));
        if(productDataModel.getImages()!=null){
            productImageService.save(productSaved.getId(),productDataModel.getImages());
        }
        ProductDataModel item=productHelper.getProductDataModel(productSaved);
        productDataModel.setImages(getImages(item));
        return item;
    }

//    @Override
//    public ProductDataModel insert(ProductDataModel productDataModel) {
//        return productHelper.getProductDataModel(productRepo.insert(productDataModel.getProductName(), productDataModel.getPrice(),
//                productDataModel.getDiscount(),productDataModel.getNote(), productDataModel.getImages(),
//                productDataModel.getNumberOfSale(),productDataModel.getCategoryId(), productDataModel.getBrandId(), false));
//    }


    @Override
    public List<ProductDataModel> findByBestSellingProducts(Integer numberOfProduct) {
        return productHelper.getListProductDataModel(productRepo.findByBestSellingProducts(numberOfProduct));
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
