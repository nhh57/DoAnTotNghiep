package com.example.ecommerce.service.impl;

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

import java.math.BigDecimal;

/**
 * <p>WarehouseServiceImpl</p>
 */
@Service
@Transactional
public class WarehouseServiceImpl implements WarehouseService {
    private static Log log = LogFactory.getLog(WarehouseServiceImpl.class);
    @Autowired
    private WarehouseRepo warehouseRepo;

    /**
     * <p>getAllWarehouses</p>
     * @param pageNumber
     * @param pageSize
     * @param keySearch
     * @return
     * @throws Exception
     */
    @Override
    public Page<Warehouse> getAllWarehouses(int pageNumber, int pageSize, String keySearch) throws Exception {
        log.info("pageNumber: " + pageNumber + " " + "pageSize: " + pageSize + " " + "keySearch: " + keySearch);
        return warehouseRepo.findAllWarehouse(keySearch, PageRequest.of(pageNumber - 1, pageSize));
    }

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
    @Override
    public void importExportProductWarehouse(String nameProduct, int statusType, BigDecimal importPirce, BigDecimal totalPrice, String note, int amount) throws Exception {
        log.info("nameProduct: " + nameProduct + " " + "statusType: " + statusType + " " + "importPirce: " + importPirce + " " + "totalPrice: " + totalPrice + " " + "note: " + note + " " + "amount: " + amount);
         warehouseRepo.spCreateImportExportWarehouse(nameProduct, statusType, importPirce, totalPrice, note, amount);
    }


}
