package com.example.ecommerce.service;

import com.example.ecommerce.model.Warehouse;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;

public interface WarehouseService {
    Page<Warehouse> getAllWarehouses(int pageNumber,int pageSize, String keySearch) throws Exception;

    void importExportProductWarehouse(String nameProduct, int statusType, BigDecimal importPirce, BigDecimal totalPrice,String note, int amount) throws Exception;
}
