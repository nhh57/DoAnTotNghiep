package com.example.ecommerce.repository;

import com.example.ecommerce.model.ShipDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ShipDetailRepo extends JpaRepository<ShipDetail, Integer> {
    @Query("SELECT sd FROM ShipDetail sd WHERE sd.accountId=?1")
    List<ShipDetail> findByAccountId(Integer accountId);
}
