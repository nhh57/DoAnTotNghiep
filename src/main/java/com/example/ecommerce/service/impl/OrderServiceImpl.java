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
        return orderHelper.getListOrderDataModel(orderRepo.findAll());
    }

    @Override
    public OrderDataModel save(OrderDataModel orderDataModel) {
        Orders orders=orderHelper.getOrders(orderDataModel);
        return orderHelper.getOrderDataModel(orderRepo.save(orders));
    }
}
