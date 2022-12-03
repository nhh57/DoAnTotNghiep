package com.example.ecommerce.repository;

import com.example.ecommerce.model.SaleDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SaleDetailRepo extends JpaRepository<SaleDetail,Integer> {
    @Query("SELECT sd FROM SaleDetail sd WHERE sd.saleId=?1")
    List<SaleDetail> findAllBySaleId(Integer saleId);
}
