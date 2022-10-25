package com.example.ecommerce.controller;

import com.example.ecommerce.model.Cart;
import com.example.ecommerce.repository.CartRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class CartController {
    @Autowired
    CartRepo cartRepo;
    // Insert
    @PostMapping("/cart/insert")
    public ResponseEntity<Cart> insert(@RequestBody Cart cart) {
        if (cartRepo.existsById(cart.getId())) {
            return ResponseEntity.badRequest().build();
        }
        cartRepo.save(cart);
        return ResponseEntity.ok(cart);
    }

}
