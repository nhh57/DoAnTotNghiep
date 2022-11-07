package com.example.ecommerce.repository;

import com.example.ecommerce.model.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepo extends JpaRepository<Orders,Integer> {
}
