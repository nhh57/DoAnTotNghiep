package com.example.ecommerce.repository;

import com.example.ecommerce.mvc.model.AccountReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AccountReportRepo extends JpaRepository<AccountReport,Integer> {
    @Query(value = " select distinct a.id, a.username, a.full_name,  " +
            " (select sum(total_money) from orders o  " +
            " where total_money in  " +
            " (select distinct total_money " +
            " from orders o1 inner join oders_detail od1 on o1.id = od1.order_id  " +
            " where o1.account_id =a.id  " +
            " and o1.order_date >= ?1 and o1.order_date < ?2)) as 'total_money'," +
            " (select count(id) from orders o " +
            " where id in " +
            " (select distinct o1.id " +
            " from orders o1 inner join oders_detail od1 on o1.id = od1.order_id " +
            " where o1.account_id =a.id and o1.order_date >= ?1 and o1.order_date < ?2)) as 'count_order' " +
            " from oders_detail od  " +
            " inner join orders o on o.id = od.order_id  " +
            " inner join account a on o.account_id = a.id  " +
            " where o.order_status = N'Đã giao' " +
            " order by total_money desc",nativeQuery = true)
    List<AccountReport> getAccountReportByDate(String startDate, String endDate);

    @Query(value = " select distinct a.id, a.username, a.full_name,  " +
            " (select sum(total_money) from orders o  " +
            " where total_money in  " +
            " (select distinct total_money " +
            " from orders o1 inner join oders_detail od1 on o1.id = od1.order_id  " +
            " where o1.account_id =a.id)) as 'total_money', " +
            " (select count(id) from orders o " +
            " where id in " +
            " (select distinct o1.id " +
            " from orders o1 inner join oders_detail od1 on o1.id = od1.order_id " +
            " where o1.account_id =a.id)) as 'count_order' " +
            " from oders_detail od  " +
            " inner join orders o on o.id = od.order_id  " +
            " inner join account a on o.account_id = a.id  " +
            " where o.order_status = N'Đã giao' " +
            " order by total_money desc",nativeQuery = true)
    List<AccountReport> getAccountReport();
}
