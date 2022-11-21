package com.example.ecommerce.repository;

import com.example.ecommerce.model.OdersDetail;
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

    @Query("SELECT od FROM Orders od WHERE od.accountId=?1 " +
            "AND od.isDeleted=0 " +
            "AND od.id=?2" +
            "AND od.orderStatus like %?3%")
    List<Orders> findAllByAccountIdAndOrderIdAndOrderStatus(Integer accountId, Integer orderId, String orderStatus);
    @Query("SELECT od FROM Orders od WHERE od.accountId=?1 " +
            "AND od.isDeleted=0 " +
            "AND od.orderStatus like %?2%")
    List<Orders> findAllByAccountIdAndOrderStatus(Integer accountId,String orderStatus);

    @Query("SELECT od FROM Orders od WHERE od.accountId=?1 " +
            "AND od.isDeleted=0 " +
            "AND od.id=?2")
    Orders existByAccountIdAndOrderId(Integer accountId, Integer orderId);

}
