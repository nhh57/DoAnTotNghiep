package com.example.ecommerce.service.impl;

import com.example.ecommerce.model.CartDetail;
import com.example.ecommerce.model.Product;
import com.example.ecommerce.model.data.CartDetailDataModel;
import com.example.ecommerce.model.helper.CartDetailHelper;
import com.example.ecommerce.repository.CartDetailRepo;
import com.example.ecommerce.repository.ProductRepo;
import com.example.ecommerce.service.CartDetailService;
import org.springframework.beans.factory.annotation.Autowired;

public class CartDetailServiceImpl implements CartDetailService {
    CartDetailHelper cartDetailHelper=new CartDetailHelper();
    @Autowired
    CartDetailRepo cartDetailRepo;

    @Autowired
    ProductRepo productRepo;

    @Override
    public CartDetailDataModel getCartDetailById(Integer cartId) {
        CartDetail cartDetail=cartDetailRepo.getCartDetail(cartId);
        CartDetailDataModel cartDetailDataModel=new CartDetailDataModel();
//        cartDetailDataModel.setProduct(productRepo.findById(cartDetail.get));
        return null;
    }

    @Override
    public CartDetailDataModel save() {
        return null;
    }
}
