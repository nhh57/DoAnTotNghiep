package com.example.ecommerce.model.helper;

import com.example.ecommerce.model.CartDetail;
import com.example.ecommerce.model.data.CartDetailDataModel;

import java.util.ArrayList;
import java.util.List;

public class CartDetailHelper {
    public CartDetailDataModel getCartDetailDataModel(CartDetail cartDetail){
            CartDetailDataModel cartDetailDataModel=new CartDetailDataModel();
            cartDetailDataModel.setId(cartDetail.getId());
            cartDetailDataModel.setCartId(cartDetail.getCartId());
            cartDetailDataModel.setProductId(cartDetail.getProductId());
            cartDetailDataModel.setPrice(cartDetail.getPrice());
            return cartDetailDataModel;
    }
    public List<CartDetailDataModel> getListCartDetailDataModel(List<CartDetail> listCartDetail){
        if(listCartDetail.size()>0){
            List<CartDetailDataModel> list=new ArrayList<>();
            for(CartDetail cartDetail:listCartDetail){
                list.add(getCartDetailDataModel(cartDetail));
            }
            return  list;
        }else{
            return  null;
        }
    }
    public CartDetail getCartDetail(CartDetailDataModel cartDetailDataModel){
        CartDetail cartDetail=new CartDetail();
        if(cartDetailDataModel!=null){
            cartDetail.setId(cartDetailDataModel.getId());
            cartDetail.setCartId(cartDetailDataModel.getCartId());
            cartDetail.setAmount(cartDetailDataModel.getAmount());
            cartDetail.setPrice(cartDetailDataModel.getPrice());
            cartDetail.setDeleted(false);
            cartDetail.setProductId(cartDetailDataModel.getProductId());
        }
        return cartDetail;
    }
}
