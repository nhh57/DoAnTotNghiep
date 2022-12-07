package com.example.ecommerce.repository;

import com.example.ecommerce.model.Statistical;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface StatisticalRepo extends JpaRepository<Statistical,Integer> {
    @Query(value = "select sum(total_money) as 'value' from orders o where year(order_date)=?1 and month(order_date)=?2 and order_status = 'Đã giao'",nativeQuery = true)
    Statistical getStatistical(String year, String month);

    @Query(value = "select year(order_date) as 'value' from orders order by year(order_date) asc limit 1",nativeQuery = true)
    Statistical getOldestYear();
}
