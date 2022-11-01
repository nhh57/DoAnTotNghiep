package com.example.ecommerce.repository;

import com.example.ecommerce.model.Warehouse;
import com.example.ecommerce.service.impl.WarehouseServiceImpl;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository("WarehouseRepo")
public interface WarehouseRepo extends JpaRepository<Warehouse, Integer> {
//    @Query(value = "SELECT id, amount, product_id, import_price, note, total_price, status, is_deleted, created_at, updated_at FROM ware_house WHERE ((?1 <> '' AND (product_id LIKE CONCAT('%',?1,'%') OR import_price LIKE CONCAT('%',?1,'%') OR note LIKE CONCAT('%',?1,'%') OR total_price LIKE CONCAT('%',?1,'%') ))) OR ?1 = ''", nativeQuery = true)
@Query(value = "SELECT * FROM ware_house  WHERE (?1 <> '' AND (product_id LIKE CONCAT('%',?1,'%') OR import_price LIKE CONCAT('%',?1,'%') OR note LIKE CONCAT('%',?1,'%') OR total_price LIKE CONCAT('%',?1,'%') )) OR ?1 = ''", nativeQuery = true)
    Page<Warehouse> findAllWarehouse(String keySearch,Pageable pageable);

}
