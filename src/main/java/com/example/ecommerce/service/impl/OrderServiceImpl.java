package com.example.ecommerce.service.impl;

import com.example.ecommerce.model.Orders;
import com.example.ecommerce.model.data.OrderDataModel;
import com.example.ecommerce.model.helper.OrderHelper;
import com.example.ecommerce.repository.OrderRepo;
import com.example.ecommerce.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    OrderRepo orderRepo;

    OrderHelper orderHelper=new OrderHelper();

    @Override
    public List<OrderDataModel> getAll() {
        List<Orders> list=orderRepo.findAll();
        List<OrderDataModel> list1=orderHelper.getListOrderDataModel(list);
        return orderHelper.getListOrderDataModel(orderRepo.findAll());
    }
}
