package com.example.ecommerce.service.impl;

import com.example.ecommerce.model.Cart;
import com.example.ecommerce.repository.CartRepo;
import com.example.ecommerce.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class CartServiceImpl implements CartService {
    @Autowired
    CartRepo cartRepo;

    @Override
    public Cart save(Cart cart) {
        return cartRepo.save(cart);
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
