package com.example.ecommerce.repository;

import com.example.ecommerce.model.Reviews;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepo extends JpaRepository<Reviews,Integer> {

    @Query("SELECT r FROM Reviews r WHERE r.productId=?1")
    List<Reviews> findByProductId(int productId);

    @Query("SELECT r FROM Reviews r WHERE r.accountId=?1")
    List<Reviews> findByAccountId(int accountId);

    @Query("SELECT r FROM Reviews r WHERE r.orderId=?1")
    List<Reviews> findByOrderId(int orderId);
}
