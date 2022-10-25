package com.example.ecommerce.repository;

import com.example.ecommerce.model.CartDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CartDetailRepo extends JpaRepository<CartDetail,Integer> {
    @Query("SELECT c FROM CartDetail c WHERE c.cartId=?1")
    List<CartDetail> getCartDetail(Integer cartId);
    @Query("SELECT cd FROM CartDetail cd WHERE cd.productId=?1")
    CartDetail existByProductId(Integer productId);
}
