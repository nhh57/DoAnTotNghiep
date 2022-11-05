package com.example.ecommerce.service;

import com.example.ecommerce.model.data.CartDetailDataModel;

import java.util.List;

public interface CartDetailService {
    List<CartDetailDataModel> getCartDetailByCartId(Integer cartId);
    CartDetailDataModel save(CartDetailDataModel cartDetailDataModel);
    void deleteByProductId(Integer cartId,Integer productId);
    void deleteAllByCartId(Integer cartId);
}
