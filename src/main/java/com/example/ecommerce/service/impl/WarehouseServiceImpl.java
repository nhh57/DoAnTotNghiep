package com.example.ecommerce.service.impl;

import com.example.ecommerce.controller.AdminController;
import com.example.ecommerce.model.Warehouse;
import com.example.ecommerce.repository.WarehouseRepo;
import com.example.ecommerce.service.WarehouseService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class WarehouseServiceImpl implements WarehouseService {
    private static Log log = LogFactory.getLog(WarehouseServiceImpl.class);
    @Autowired
    private WarehouseRepo warehouseRepo;
    @Override
    public Page<Warehouse> getAllWarehouses(int pageNumber, int pageSize, String keySearch) throws Exception{
        log.info("pageNumber: "+pageNumber +" "+ "pageSize: "+pageSize+ " "+"keySearch: "+keySearch);
        return warehouseRepo.findAllWarehouseByKeySearch(keySearch, PageRequest.of(pageNumber - 1, pageSize));
    }


}
