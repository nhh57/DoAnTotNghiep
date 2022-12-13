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
            "AND od.id=?2 " +
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

    @Query("SELECT o FROM Orders o WHERE o.id=?1 order by o.isDeleted asc,  o.orderDate desc")
    List<Orders> findByOrderId(Integer orderId);
    @Query("SELECT o FROM Orders o WHERE o.id=?1 order by o.isDeleted asc,  o.orderDate desc")
    Page<Orders> findByOrderId(Pageable page, Integer orderId);

    @Query("SELECT o FROM Orders o order by o.isDeleted asc,  o.orderDate desc")
    List<Orders> findAllCustom();
    @Query("SELECT o FROM Orders o order by o.isDeleted asc,  o.orderDate desc")
    Page<Orders> findAllCustom(Pageable page);

    @Query("SELECT od FROM Orders od WHERE od.accountId=?1")
    List<Orders> findAllByAccountId(Integer accountId);

    @Query(value = "SELECT * FROM Orders od WHERE od.account_id=?1 and od.order_date >= ?2 and od.order_date < ?3",nativeQuery = true)
    List<Orders> findAllByAccountIdAndDate(Integer accountId,String startDate,String endDate);

}
