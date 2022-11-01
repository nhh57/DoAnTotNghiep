package com.example.ecommerce.controller;

import com.example.ecommerce.model.Product;
import com.example.ecommerce.model.Warehouse;
import com.example.ecommerce.response.BaseResponse;
import com.example.ecommerce.service.WarehouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/api/admin")
public class AdminController {
    @Autowired
    private WarehouseService warehouseSsservice;
    private static Log log = LogFactory.getLog(AdminController.class);

    @RequestMapping(value = "/warehouse/get-all-product", method = RequestMethod.GET, produces = {
            MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<BaseResponse> getAllAccount(
            @RequestParam(name = "page", required = false, defaultValue = "1") int page,
            @RequestParam(name = "size", required = false, defaultValue = "1") int size,
            @RequestParam(name = "key_search", required = false, defaultValue = "") String keySearch) throws Exception {
        BaseResponse response = new BaseResponse();
        List<Warehouse> listData = new ArrayList<>();
        try {
            Page<Warehouse> pageProducts = warehouseSsservice.getAllWarehouses(page, size, keySearch);
            listData = pageProducts.getContent();
            response.setData(listData);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ResponseEntity<BaseResponse>(response, HttpStatus.OK);
    }
}
