package com.example.ecommerce.repository;

import com.example.ecommerce.model.OdersDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderDetailRepo extends JpaRepository<OdersDetail,Integer> {
    @Query("SELECT od FROM OdersDetail od WHERE od.orderId=?1")
    List<OdersDetail> findAllByOrderId(Integer orderId);
}
