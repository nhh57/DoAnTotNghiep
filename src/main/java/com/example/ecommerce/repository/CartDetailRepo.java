package com.example.ecommerce.repository;

import com.example.ecommerce.model.CartDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CartDetailRepo extends JpaRepository<CartDetail,Integer> {
    @Query(value="SELECT * FROM cart WHERE cart_id=?1",nativeQuery = true)
    CartDetail getCartDetail(Integer cartId);
}
