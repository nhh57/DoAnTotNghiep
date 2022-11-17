package com.example.ecommerce.service;

import com.example.ecommerce.model.Warehouse;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;

/**
 * <p>WarehouseService</p>
 */
public interface WarehouseService {
    /**
     * <p>getAllWarehouses</p>
     * @param pageNumber
     * @param pageSize
     * @param keySearch
     * @return
     * @throws Exception
     */
    Page<Warehouse> getAllWarehouses(int pageNumber,int pageSize, String keySearch) throws Exception;

    /**
     * <p>importExportProductWarehouse</p>
     * @param nameProduct
     * @param statusType
     * @param importPirce
     * @param totalPrice
     * @param note
     * @param amount
     * @throws Exception
     */
    void importExportProductWarehouse(String nameProduct, int statusType, BigDecimal importPirce, BigDecimal totalPrice,String note, int amount) throws Exception;
}
