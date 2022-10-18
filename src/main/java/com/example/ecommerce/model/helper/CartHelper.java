package com.example.ecommerce.model.helper;

import com.example.ecommerce.model.Cart;
import com.example.ecommerce.model.data.CartDataModel;

public class CartHelper {
    public Cart getCart(CartDataModel cartDataModel){
        Cart cart=new Cart();
        if (cartDataModel != null) {
            cart.setId(cartDataModel.getId());
            cart.setDeleted(cartDataModel.getIsDeleted());
        }
        return cart;
    }
    public CartDataModel getCartDataModel(Cart cart){
        CartDataModel cartDataModel=new CartDataModel();
        if (cart != null) {
            cartDataModel.setId(cart.getId());
            cartDataModel.setIsDeleted(cart.getDeleted());
        }
        return cartDataModel;
    }
}
