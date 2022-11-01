package com.example.ecommerce.service;

import com.example.ecommerce.model.Warehouse;
import org.springframework.data.domain.Page;

public interface WarehouseService {
    Page<Warehouse> getAllWarehouses(int pageNumber,int pageSize, String keySearch) throws Exception;
}
