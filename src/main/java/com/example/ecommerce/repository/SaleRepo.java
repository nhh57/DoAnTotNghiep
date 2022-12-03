package com.example.ecommerce.repository;

import com.example.ecommerce.model.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SaleRepo extends JpaRepository<Sale,Integer> {

    //Ngày gần nhất, giờ gần nhất
    @Query(value="SELECT * FROM sale where is_deleted = 0 order by sale_date_start asc, sale_time_start asc limit 1",nativeQuery = true)
    Sale findByRecentDay();
}
