package com.example.ecommerce.repository;

import com.example.ecommerce.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepo extends JpaRepository<Cart, Integer> {

}
