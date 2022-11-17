package com.example.ecommerce.service.impl;

import com.example.ecommerce.controller.AdminController;
import com.example.ecommerce.model.data.AccountOauthDataModel;
import com.example.ecommerce.model.data.AdminDataModel;
import com.example.ecommerce.repository.AdminDataModelRepo;
import com.example.ecommerce.service.AdminDateModelService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
@Transactional
public class AdminDateModelServiceImpl implements AdminDateModelService {
    private static Log log = LogFactory.getLog(AdminDateModelServiceImpl.class);
    @Autowired
private AdminDataModelRepo adminDataModelRepo;

    @Override
    public List<AdminDataModel> getTotalRevenue(String fromDate, String toDate, int reportType) throws Exception {
        log.info("fromDateDB"+fromDate);
        log.info("fromDateDB"+toDate);
        return adminDataModelRepo.getTotalRevenue(fromDate,toDate,reportType);
    }

}
