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
import java.util.List;

/**
 * <p>WarehouseRepo</p>
 */
@Repository("WarehouseRepo")
public interface WarehouseRepo extends JpaRepository<Warehouse, Integer> {
    /**
     * <p>findAllWarehouse</p>
     * @param keySearch
     * @param pageable
     * @return Page
     */
    @Query(value = "SELECT * FROM ware_house  WHERE (?1 <> '' AND (product_id LIKE CONCAT('%',?1,'%') OR import_price LIKE CONCAT('%',?1,'%') OR note LIKE CONCAT('%',?1,'%') OR total_price LIKE CONCAT('%',?1,'%') )) OR ?1 = ''", nativeQuery = true)
    Page<Warehouse> findAllWarehouse(String keySearch,Pageable pageable);

    /**
     * <p>spCreateImportExportWarehouse</p>
     * @param nameProduct
     * @param statusType
     * @param importPirce
     * @param totalPrice
     * @param note
     * @param amount
     */
    @Modifying(clearAutomatically = true)
    @Query(value = "{CALL sp_c_create_import_export_warehouse(:nameProduct, :statusType, :importPirce, :totalPrice, :note, :amount)}", nativeQuery = true)
    void spCreateImportExportWarehouse(@Param("nameProduct") String nameProduct,
                                                        @Param("statusType") int statusType,
                                                        @Param("importPirce") BigDecimal importPirce,
                                                        @Param("totalPrice") BigDecimal totalPrice,
                                                        @Param("note") String note,
                                                        @Param("amount") int amount) ;

    @Query("SELECT w FROM Warehouse w WHERE w.productId=?1")
    Warehouse findByProductId(Integer productId);

}
