package com.example.ecommerce.repository;

import com.example.ecommerce.mvc.model.ProductReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface ProductReportRepo extends JpaRepository<ProductReport,Integer> {
    @Query(value = "select distinct p.id, p.product_name , p.images, " +
            " (select SUM(od1.amount) from oders_detail od1 inner join orders o1  on o1.id = od1.order_id where od1.product_id = p.id " +
            " and o1.order_date  >= ?1 and o1.order_date < ?2) as 'number_of_sale' " +
            " from oders_detail od " +
            " inner join orders o  on o.id = od.order_id  inner join product p   on p.id =od.product_id " +
            " where o.order_status = N'Đã giao' order by number_of_sale DESC",nativeQuery = true)
    List<ProductReport> getProductReportByDate(String startDate, String endDate);

    @Query(value = "select distinct p.id, p.product_name , p.images, " +
            " (select SUM(od1.amount) from oders_detail od1 inner join orders o1  on o1.id = od1.order_id " +
            " where od1.product_id = p.id) as 'number_of_sale' " +
            " from oders_detail od " +
            " inner join orders o  on o.id = od.order_id  inner join product p   on p.id =od.product_id " +
            " where o.order_status = N'Đã giao' order by number_of_sale DESC",nativeQuery = true)
    List<ProductReport> getProductReport();
}
