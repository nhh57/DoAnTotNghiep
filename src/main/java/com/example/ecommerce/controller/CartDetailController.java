package com.example.ecommerce.controller;

import com.example.ecommerce.model.data.CartDetailDataModel;
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

    @GetMapping("/cart-detail/get-all/{id}")
    public ResponseEntity<List<CartDetailDataModel>> getCartDetail(@PathVariable("id")Integer cartId){
        return ResponseEntity.ok(cartDetailService.getCartDetailByCartId(cartId));
    }

    @PostMapping("/cart-detail/save")
    public ResponseEntity<CartDetailDataModel> save(@RequestBody CartDetailDataModel cartDetailDataModel){
        return ResponseEntity.ok(cartDetailService.save(cartDetailDataModel));
    }
}
