package com.example.ecommerce.repository;

import com.example.ecommerce.model.OdersDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderDetailRepo extends JpaRepository<OdersDetail,Integer> {
    @Query("SELECT od FROM OdersDetail od WHERE od.orderId=?1 AND od.ordersByOrderId.isDeleted=0")
    List<OdersDetail> findAllByOrderId(Integer orderId);

    @Query("SELECT od FROM OdersDetail od WHERE od.ordersByOrderId.accountId=?1 " +
            "AND od.ordersByOrderId.isDeleted=0 " +
            "AND od.ordersByOrderId.id=?2" +
            "AND od.ordersByOrderId.orderStatus like %?3%")
    List<OdersDetail> findAllByAccountIdAndOrderIdAndOrderStatus(Integer accountId,Integer orderId,String orderStatus);
    @Query("SELECT od FROM OdersDetail od WHERE od.ordersByOrderId.accountId=?1 " +
            "AND od.ordersByOrderId.isDeleted=0 " +
            "AND od.ordersByOrderId.orderStatus like %?2%")
    List<OdersDetail> findAllByAccountIdAndOrderStatus(Integer accountId,String orderStatus);

}
