package com.example.ecommerce.repository;

import com.example.ecommerce.model.SaleDetail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SaleDetailRepo extends JpaRepository<SaleDetail,Integer> {
    @Query("SELECT sd FROM SaleDetail sd WHERE sd.saleId=?1")
    List<SaleDetail> findAllBySaleId(Integer saleId);
    @Query("SELECT sd FROM SaleDetail sd WHERE sd.saleId=?1")
    Page<SaleDetail> findAllBySaleId(Pageable pageable, Integer saleId);

    @Query("SELECT sd FROM SaleDetail sd WHERE sd.saleId=?1 and sd.product.productName like %?2%")
    List<SaleDetail> findAllBySaleIdAndProductName(Integer saleId,String productName);
    @Query("SELECT sd FROM SaleDetail sd WHERE sd.saleId=?1 and sd.product.productName like %?2%")
    Page<SaleDetail> findAllBySaleIdAndProductName(Pageable pageable,Integer saleId,String productName);

    @Query("SELECT sd FROM SaleDetail sd WHERE sd.saleId=?1 and sd.productId=?2")
    SaleDetail findBySaleIdAndProductId(Integer saleId,Integer productId);
}
