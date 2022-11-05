package com.example.ecommerce.controller;

import com.example.ecommerce.model.data.CartDetailDataModel;
import com.example.ecommerce.model.helper.CartHelper;
import com.example.ecommerce.model.result.CartResult;
import com.example.ecommerce.service.CartDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CartDetailController {
    @Autowired
    CartDetailService cartDetailService;

    CartHelper cartHelper=new CartHelper();

    @GetMapping("/cart-detail/get-all/{id}")
    public ResponseEntity<CartResult> getCartDetail(@PathVariable("id")Integer cartId){
        List<CartDetailDataModel> list=cartDetailService.getCartDetailByCartId(cartId);
        CartResult cartResult=new CartResult();
        cartResult.setListCartDetail(list);
        cartResult.setTotalMoney(cartHelper.getTotalMoney(list));
        cartResult.setNumberOfCart(cartHelper.getNumberOfCart(list));
        return ResponseEntity.ok(cartResult);
    }

    @PostMapping("/cart-detail/save")
    public ResponseEntity<CartDetailDataModel> save(@RequestBody CartDetailDataModel cartDetailDataModel){
        return ResponseEntity.ok(cartDetailService.save(cartDetailDataModel));
    }
    @DeleteMapping("/cart-detail/delete/{cartId}/{productId}")
    public ResponseEntity<Void> deleteItem(@PathVariable("cartId")Integer cartId,
                                           @PathVariable("productId")Integer productId){
        cartDetailService.deleteByProductId(cartId,productId);
        return ResponseEntity.ok().build();
    }
    @DeleteMapping("/cart-detail/delete-all/{cartId}")
    public ResponseEntity<Void> deleteAll(@PathVariable("cartId")Integer cartId){
        cartDetailService.deleteAllByCartId(cartId);
        return ResponseEntity.ok().build();
    }
}
