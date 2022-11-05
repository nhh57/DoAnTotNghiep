package com.example.ecommerce.service.impl;

import com.example.ecommerce.model.CartDetail;
import com.example.ecommerce.model.data.CartDetailDataModel;
import com.example.ecommerce.model.helper.CartDetailHelper;
import com.example.ecommerce.repository.CartDetailRepo;
import com.example.ecommerce.service.CartDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class CartDetailServiceImpl implements CartDetailService {

    @Autowired
    CartDetailRepo cartDetailRepo;

    CartDetailHelper cartDetailHelper=new CartDetailHelper();


    @Override
    public List<CartDetailDataModel> getCartDetailByCartId(Integer cartId) {
        List<CartDetail> listCartDetail=cartDetailRepo.getCartDetail(cartId);
        return cartDetailHelper.getListCartDetailDataModel(listCartDetail);
    }

    @Override
    public CartDetailDataModel save(CartDetailDataModel cartDetailDataModel) {
        CartDetail cartDetail=cartDetailRepo.existByProductId(cartDetailDataModel.getProductId());
        if(cartDetail==null){
            CartDetail resultSaved= cartDetailRepo.save(cartDetailHelper.getCartDetail(cartDetailDataModel));
            return cartDetailHelper.getCartDetailDataModel(resultSaved);
        }else{
            cartDetail.setAmount(cartDetail.getAmount()+cartDetailDataModel.getAmount());
            CartDetail resultSaved= cartDetailRepo.save(cartDetail);
            return cartDetailHelper.getCartDetailDataModel(resultSaved);
        }
    }

    @Override
    public void deleteByProductId(Integer cartId,Integer productId) {
        cartDetailRepo.deleteByProductId(cartId,productId);
    }

    @Override
    public void deleteAllByCartId(Integer cartId) {
        cartDetailRepo.deleteAllByCartId(cartId);
    }
}
