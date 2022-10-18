package com.example.ecommerce.service;

import com.example.ecommerce.model.data.CartDetailDataModel;

public interface CartDetailService {
    CartDetailDataModel getCartDetailById(Integer cartId);
    CartDetailDataModel save();
}
