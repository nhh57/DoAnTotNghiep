package com.example.ecommerce.service;

import com.example.ecommerce.model.data.CartDataModel;

public interface CartService {
    CartDataModel save(CartDataModel cartDataModel);
    Boolean deleteById(Integer cartId);
}
