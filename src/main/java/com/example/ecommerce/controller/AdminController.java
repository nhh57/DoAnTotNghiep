package com.example.ecommerce.controller;

import com.example.ecommerce.common.Utils;
import com.example.ecommerce.model.Account;
import com.example.ecommerce.model.Product;
import com.example.ecommerce.model.Warehouse;
import com.example.ecommerce.model.data.AdminDataModel;
import com.example.ecommerce.request.AccountRequest;
import com.example.ecommerce.request.ProductWarehouseRequest;
import com.example.ecommerce.response.AccountResponse;
import com.example.ecommerce.response.BaseResponse;
import com.example.ecommerce.service.AccountService;
import com.example.ecommerce.service.AdminDateModelService;
import com.example.ecommerce.service.WarehouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@RestController
@RequestMapping("/api/admin")
public class AdminController {
    private static Log log = LogFactory.getLog(AdminController.class);
    @Autowired
    private WarehouseService warehouseSsservice;

    @Autowired
    private AdminDateModelService adminDateModelService;
    @Autowired
    private AccountService accountService;

    @RequestMapping(value = "/warehouse/get-all-product", method = RequestMethod.GET, produces = {
            MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<BaseResponse> getAllAccount(
            @RequestParam(name = "page", required = false, defaultValue = "1") int page,
            @RequestParam(name = "size", required = false, defaultValue = "1") int size,
            @RequestParam(name = "key_search", required = false, defaultValue = "") String keySearch) throws Exception {
        BaseResponse response = new BaseResponse();

        try {
            Page<Warehouse> pageProducts = warehouseSsservice.getAllWarehouses(page, size, keySearch);
            List<Warehouse> listData = pageProducts.getContent();
            response.setData(listData);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<BaseResponse>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/warehouse/import-export-product", method = RequestMethod.POST, produces = {
            MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<BaseResponse> createAccount(@Valid @RequestBody ProductWarehouseRequest request) throws Exception {
        BaseResponse response = new BaseResponse();
        warehouseSsservice.importExportProductWarehouse(request.getNameProduct(),request.getStatusType(),request.getImportPirce(),request.getTotalPrice(),request.getNote(),request.getAmount());
        return new ResponseEntity<BaseResponse>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/warehouse/get-all-account", method = RequestMethod.GET, produces = {
            MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<BaseResponse> getAllAccount(
            @RequestParam(name = "page", required = false, defaultValue = "1") int page,
            @RequestParam(name = "size", required = false, defaultValue = "1") int size,
            @RequestParam(name = "key_search", required = false, defaultValue = "") String keySearch,
            @RequestParam(name = "is_deleted", required = false, defaultValue = "-1") int isDeleted) throws Exception {
        BaseResponse response = new BaseResponse();
        List<Account> listAccounts = accountService.getAllAccount(keySearch, isDeleted,2,page,size);
        List<AccountResponse> responseList = new AccountResponse().mapToListResponse(listAccounts);
        response.setData(responseList);
        return new ResponseEntity<BaseResponse>(response, HttpStatus.OK);
    }

    /**
     * <p>getTotalRevenue</p>
     * @param fromDate
     * @param toDate
     * @param reportType
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/get-total_revenue", method = RequestMethod.GET, produces = {
            MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<BaseResponse> getTotalRevenue (
            @RequestParam(name = "from_date", required = false, defaultValue = "") String fromDate,
            @RequestParam(name = "to_date", required = false, defaultValue = "") String toDate,
            @RequestParam(name = "report_type", required = false, defaultValue = "3") int reportType) throws Exception {
        BaseResponse response = new BaseResponse();
        List<AdminDataModel> dataModelList = adminDateModelService.getTotalRevenue(fromDate,toDate,reportType);
        response.setData(dataModelList);
        return new ResponseEntity<BaseResponse>(response, HttpStatus.OK);

    }
}
