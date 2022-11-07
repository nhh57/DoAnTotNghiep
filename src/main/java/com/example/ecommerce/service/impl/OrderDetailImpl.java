package com.example.ecommerce.service.impl;

import com.example.ecommerce.model.OdersDetail;
import com.example.ecommerce.model.data.OrderDetailDataModel;
import com.example.ecommerce.model.helper.OrderDetailHelper;
import com.example.ecommerce.repository.OrderDetailRepo;
import com.example.ecommerce.service.OrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderDetailImpl implements OrderDetailService {
    @Autowired
    OrderDetailRepo orderDetailRepo;
    OrderDetailHelper orderDetailHelper=new OrderDetailHelper();
    @Override
    public List<OrderDetailDataModel> getAll(Integer orderId) {
        return orderDetailHelper.getListOrderDetailDataModel(orderDetailRepo.findAll());
    }

    @Override
    public OrderDetailDataModel save(OrderDetailDataModel orderDetailDataModel) {
        OdersDetail odersDetail=orderDetailHelper.getOrderDetail(orderDetailDataModel);
        return orderDetailHelper.getOrderDetailDataModel(orderDetailRepo.save(odersDetail));
    }
}
