package com.example.ecommerce.repository;

import com.example.ecommerce.model.Warehouse;
import com.example.ecommerce.model.data.AccountOauthDataModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository("WarehouseRepo")
public interface WarehouseRepo extends JpaRepository<Warehouse, Integer> {
@Query(value = "SELECT * FROM ware_house  WHERE (?1 <> '' AND (product_id LIKE CONCAT('%',?1,'%') OR import_price LIKE CONCAT('%',?1,'%') OR note LIKE CONCAT('%',?1,'%') OR total_price LIKE CONCAT('%',?1,'%') )) OR ?1 = ''", nativeQuery = true)
    Page<Warehouse> findAllWarehouse(String keySearch,Pageable pageable);

    @Modifying(clearAutomatically = true)
    @Query(value = "{CALL sp_c_create_import_export_warehouse(:nameProduct, :statusType, :importPirce, :totalPrice, :note, :amount)}", nativeQuery = true)
    void spCreateImportExportWarehouse(@Param("nameProduct") String nameProduct,
                                                        @Param("statusType") int statusType,
                                                        @Param("importPirce") BigDecimal importPirce,
                                                        @Param("totalPrice") BigDecimal totalPrice,
                                                        @Param("note") String note,
                                                        @Param("amount") int amount) ;

}
