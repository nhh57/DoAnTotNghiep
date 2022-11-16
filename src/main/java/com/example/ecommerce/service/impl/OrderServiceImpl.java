package com.example.ecommerce.service.impl;

import com.example.ecommerce.model.Orders;
import com.example.ecommerce.model.data.OrderDataModel;
import com.example.ecommerce.model.helper.OrderHelper;
import com.example.ecommerce.repository.OrderRepo;
import com.example.ecommerce.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {
    @Autowired
    OrderRepo orderRepo;

    OrderHelper orderHelper=new OrderHelper();


    @Override
    public List<OrderDataModel> findAllAdmin(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page-1, size);
        Page<Orders> pageOrders=orderRepo.findAll(pageable);
        List<Orders> list=pageOrders.getContent();
        List<OrderDataModel> listResult=orderHelper.getListOrderDataModel(list);
        return listResult;
    }

    @Override
    public List<OrderDataModel> findOrdersExist(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page-1, size);
        Page<Orders> pageOrders=orderRepo.findOrdersExist(pageable);
        List<Orders> list=pageOrders.getContent();
        List<OrderDataModel> listResult=orderHelper.getListOrderDataModel(list);
        return listResult;
    }

    @Override
    public OrderDataModel findById(Integer orderId) {
        return orderHelper.getOrderDataModel(orderRepo.findById(orderId).get());
    }

    @Override
    public boolean existsById(Integer id) {
        return orderRepo.existsById(id);
    }

    @Override
    public OrderDataModel save(OrderDataModel orderDataModel) {
        Orders orders=orderHelper.getOrders(orderDataModel);
        return orderHelper.getOrderDataModel(orderRepo.save(orders));
    }

}
