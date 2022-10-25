package com.example.ecommerce.service;

import com.example.ecommerce.model.Cart;
import com.example.ecommerce.model.data.CartDataModel;

public interface CartService {
    Cart save(Cart cart);

    Boolean deleteById(Integer cartId);
}
