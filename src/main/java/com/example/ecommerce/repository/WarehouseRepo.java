package com.example.ecommerce.repository;

import com.example.ecommerce.model.Warehouse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository("WarehouseRepo")
public interface WarehouseRepo extends JpaRepository<Warehouse, Integer> {
    @Query(value = "SELECT wh.id, wh.amount, wh.product_id, wh.import_price, wh.note, wh.total_price, wh.status, wh.is_deleted, wh.created_at, wh.updated_at FROM ware_house wh WHERE ((?1 <> '' AND (wh.product_id LIKE CONCAT('%',?1,'%') OR wh.import_price LIKE CONCAT('%',?1,'%') OR wh.note LIKE CONCAT('%',?1,'%') OR wh.total_price LIKE CONCAT('%',?1,'%') )) OR ?1 = '')", nativeQuery = true)
    Page<Warehouse> findAllWarehouseByKeySearch(String keySearch,Pageable pageable);
}
