package com.example.ecommerce.service;

import com.example.ecommerce.model.data.OrderDetailDataModel;

import java.util.List;

public interface OrderDetailService {
    List<OrderDetailDataModel> getAll(Integer orderId);
    OrderDetailDataModel save(OrderDetailDataModel orderDetailDataModel);
}
