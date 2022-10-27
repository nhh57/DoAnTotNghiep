package com.example.ecommerce.service;

import com.example.ecommerce.model.Cart;

public interface CartService {
    Cart save(Cart cart);

    Boolean deleteById(Integer cartId);
}
