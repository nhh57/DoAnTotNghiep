package com.java.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import com.java.entity.Cart;

public interface CartRepository extends JpaRepository<Cart, Integer> {
    Cart findByUserId(Integer userId);
}