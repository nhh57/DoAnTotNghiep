package com.example.ecommerce.repository;

import com.example.ecommerce.model.data.AdminDataModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface AdminDataModelRepo extends JpaRepository<AdminDataModel, Date> {
    @Query(value = "{CALL thongketien(:fromDateString,:toDateString,:reportType)}", nativeQuery = true)
    List<AdminDataModel> getTotalRevenue(@Param("fromDateString") String fromDateString,
                                         @Param("toDateString") String toDateString,
                                         @Param("reportType") int reportType) ;
}
