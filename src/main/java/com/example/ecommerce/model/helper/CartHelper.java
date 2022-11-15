package com.example.ecommerce.model.helper;

import com.example.ecommerce.model.Cart;
import com.example.ecommerce.model.CartDetail;
import com.example.ecommerce.model.Product;
import com.example.ecommerce.model.data.CartDataModel;
import com.example.ecommerce.model.data.CartDetailDataModel;

import java.util.List;

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
    public int getNumberOfCart(List<CartDetailDataModel> list){
        int numberOfCart=0;
        if(list == null || list.isEmpty()){
            return 0;
        }
        for(CartDetailDataModel item:list){
            numberOfCart+=item.getAmount();
        }
        return  numberOfCart;
    }
    public int getTotalMoney(List<CartDetailDataModel> list){
        int totalMoney=0;
        if(list == null || list.isEmpty()){
            return 0;
        }
        for(CartDetailDataModel item:list){
            totalMoney+=(item.getAmount()*item.getProductDataModel().getPrice());
        }
        return  totalMoney;
    }

    public int getTotalMoneyCart(List<CartDetail> list){
        int totalMoney=0;
        if(list == null || list.isEmpty()){
            return 0;
        }
        for(CartDetail item:list){
            totalMoney+=(item.getAmount()*item.getProductByProductId().getPrice());
        }
        return totalMoney;
    }
    public int getNumberOfListCart(List<CartDetail> list){
        int numberOfCart=0;
        if(list == null || list.isEmpty()){
            return 0;
        }
        for(CartDetail item:list){
            numberOfCart+=item.getAmount();
        }
        return  numberOfCart;
    }

    public int getTotalMoneyOfOneProduct(CartDetail cartDetail){
        return  cartDetail.getAmount()*cartDetail.getProductByProductId().getPrice();
    }
    public boolean checkNullProductByProductId(List<CartDetail> listCart){
        for(CartDetail item:listCart){
            if(item.getProductByProductId()==null) {
                return true;
            }
        }
        return false;
    }
}
