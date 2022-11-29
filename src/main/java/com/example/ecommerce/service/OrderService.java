package com.example.ecommerce.service;

import com.example.ecommerce.model.data.OrderDataModel;

import java.util.List;

public interface OrderService {
    List<OrderDataModel> findAllAdmin(Integer page, Integer size);
    List<OrderDataModel> findOrdersExist(Integer page, Integer size);
    OrderDataModel findById(Integer orderId);
    boolean existsById(Integer id);
    OrderDataModel save(OrderDataModel orderDataModel);
}
