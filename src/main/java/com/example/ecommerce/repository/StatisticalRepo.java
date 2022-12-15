package com.example.ecommerce.repository;

import com.example.ecommerce.model.Statistical;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface StatisticalRepo extends JpaRepository<Statistical,Integer> {
    @Query(value = "select sum(total_money-delivery_charges) as 'value' from orders o where year(order_date)=?1 and month(order_date)=?2 and order_status = 'Đã giao' and is_deleted=0",nativeQuery = true)
    Statistical getStatisticalByYear(String year, String month);

    @Query(value = "select year(order_date) as 'value' from orders order by year(order_date) asc limit 1",nativeQuery = true)
    Statistical getOldestYear();

    @Query(value = "select sum(total_money-delivery_charges) as 'value' from orders o where year(order_date)=?1 and month(order_date)=?2 and day(order_date)=?3 and order_status = 'Đã giao' and is_deleted=0",nativeQuery = true)
    Statistical getStatisticalByMonth(String year,String month, String day);

    @Query(value = "select sum(total_money-delivery_charges) as 'value' from orders o where year(order_date)=?1 and month(order_date)=?2 and day(order_date)=?3 and order_status = 'Đã giao' and is_deleted=0",nativeQuery = true)
    Statistical getStatisticalFromTo(String year,String month, String day);
}
