package com.example.ecommerce.repository;

import com.example.ecommerce.model.Orders;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderRepo extends JpaRepository<Orders,Integer> {
    @Query("SELECT p FROM Orders p WHERE p.isDeleted = 0")
    Page<Orders> findOrdersExist(Pageable pageable);
    @Query("SELECT p FROM Orders p WHERE p.isDeleted = 0")
    List<Orders> findOrdersExist();
}
