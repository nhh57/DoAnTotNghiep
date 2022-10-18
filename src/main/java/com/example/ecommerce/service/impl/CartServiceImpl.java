package com.example.ecommerce.service.impl;

import com.example.ecommerce.model.Cart;
import com.example.ecommerce.model.data.CartDataModel;
import com.example.ecommerce.model.helper.CartHelper;
import com.example.ecommerce.repository.CartRepo;
import com.example.ecommerce.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;

public class CartServiceImpl implements CartService {
    CartHelper cartHelper=new CartHelper();
    @Autowired
    CartRepo cartRepo;

    @Override
    public CartDataModel save(CartDataModel cartDataModel) {
        Cart cart=cartRepo.save(cartHelper.getCart(cartDataModel));
        return cartHelper.getCartDataModel(cart);
    }

    @Override
    public Boolean deleteById(Integer cartId) {
        Cart cart=cartRepo.findById(cartId).get();
        if(cart!=null){
            cart.setDeleted(true);
            cartRepo.save(cart);
            return true;
        }else{
            return false;
        }
    }
}
