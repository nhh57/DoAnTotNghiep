package com.java.service;

import com.java.entity.Cart;
import com.java.entity.CartItem;
import com.java.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    public Cart getCartByUserId(Integer userId) {
        return cartRepository.findByUserId(userId);
    }

    public Cart addItemToCart(Integer userId, CartItem item) {
        Cart cart = cartRepository.findByUserId(userId);
        if (cart == null) {
            cart = new Cart();
            cart.setUserId(userId);
        }
        cart.addItem(item);
        return cartRepository.save(cart);  // Lưu giỏ hàng cùng các item
    }

    public void removeItemFromCart(Integer userId, Integer itemId) {
        Cart cart = cartRepository.findByUserId(userId);
        if (cart != null) {
            CartItem item = cart.getItems()
                    .stream()
                    .filter(i -> i.getId().equals(itemId))
                    .findFirst()
                    .orElse(null);
            if (item != null) {
                cart.removeItem(item);
                cartRepository.save(cart);
            }
        }
    }
}
