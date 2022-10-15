package com.example.ecommerce.model.helper;

import com.example.ecommerce.model.Categories;
import com.example.ecommerce.model.Product;
import com.example.ecommerce.model.data.BrandDataModel;
import com.example.ecommerce.model.data.CategoriesDataModel;
import com.example.ecommerce.model.data.ProductDataModel;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ProductHelper {
    CategoriesHelper categoriesHelper=new CategoriesHelper();
    BrandHelper brandHelper=new BrandHelper();
    public Product getProduct(ProductDataModel productDataModel){
        Product product=new Product();
        product.setId(productDataModel.getId());
        product.setProductName(productDataModel.getProductName());
        product.setImages(productDataModel.getImages());
        product.setNote(productDataModel.getNote());
        product.setPrice(productDataModel.getPrice());
        product.setDiscount(productDataModel.getDiscount());
        product.setNumberOfSale(productDataModel.getNumberOfSale());
        product.setBrandId(productDataModel.getBrandId());
        product.setCategoryId(productDataModel.getCategoryId());
        product.setDeleted(productDataModel.getIsDeleted());
        return product;
    }
    public ProductDataModel getProductDataModel(Product product){
        ProductDataModel productDataModel=new ProductDataModel();
        productDataModel.setId(product.getId());
        productDataModel.setProductName(product.getProductName());
        productDataModel.setImages(product.getImages());
        productDataModel.setNote(product.getNote());
        productDataModel.setPrice(product.getPrice());
        productDataModel.setDiscount(product.getDiscount());
        productDataModel.setPromotionPrice(getPromotionPrice(product.getPrice(),product.getDiscount()));
        productDataModel.setNumberOfSale(product.getNumberOfSale());
        productDataModel.setBrandId(product.getBrandId());
        productDataModel.setCategoryId(product.getCategoryId());
        if(product.getCategoriesByCategoryId()!=null){
            productDataModel.setCategories(categoriesHelper.getCategoriesDataModel(product.getCategoriesByCategoryId()));
        }
        if(product.getBrandByBrandId()!=null){
            productDataModel.setBrand(brandHelper.getBrandDataModel(product.getBrandByBrandId()));
        }

        productDataModel.setIsDeleted(product.getDeleted()==null?false:product.getDeleted());
        return productDataModel;
    }
    public List<ProductDataModel> getListProductDataModel(List<Product> listProduct){
        List<ProductDataModel> listProductDataModel=new ArrayList<>();
        for(Product product:listProduct){
            listProductDataModel.add(getProductDataModel(product));
        }
        return listProductDataModel;
    }
    public BigDecimal getPromotionPrice(BigDecimal price, Integer discount){
        return BigDecimal.valueOf(price.doubleValue()*discount/100);
    }
}
