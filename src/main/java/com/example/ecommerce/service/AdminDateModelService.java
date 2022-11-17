package com.example.ecommerce.service;

import com.example.ecommerce.model.data.AccountOauthDataModel;
import com.example.ecommerce.model.data.AdminDataModel;

import java.util.List;

public interface AdminDateModelService {

    List<AdminDataModel> getTotalRevenue(String fromDate, String toDate, int reportType)  throws Exception;
}
