package com.example.ecommerce.model.helper;

import com.example.ecommerce.common.Utils;
import com.example.ecommerce.model.Orders;
import com.example.ecommerce.model.data.OrderDataModel;

import java.util.ArrayList;
import java.util.List;

public class OrderHelper {
    AccountHelper accountHelper=new AccountHelper();
    ShipDetailHelper shipDetailHelper=new ShipDetailHelper();
    public OrderDataModel getOrderDataModel(Orders orders){
        OrderDataModel orderDataModel=new OrderDataModel();
        orderDataModel.setId(orders.getId());
        orderDataModel.setOrderDate(orders.getOrderDate()!=null?Utils.convertDateTimeToString(orders.getOrderDate()):null);
        orderDataModel.setNote(orders.getNote());
        orderDataModel.setOrderStatus(orders.getOrderStatus());
        orderDataModel.setTotalMoney(orders.getTotalMoney().intValue());
        orderDataModel.setDeliveryDate(orders.getDeliveryDate()!=null?Utils.convertDateTimeToString(orders.getDeliveryDate()):null);
        orderDataModel.setAccountId(orders.getAccountId());
        orderDataModel.setShipDetailId(orders.getShipDetailId());
        orderDataModel.setIsDeleted(orders.getIsDeleted());
        orderDataModel.setAccount(orders.getAccountByAccountId()!=null?accountHelper.getAccountInforModel(orders.getAccountByAccountId()):null);
        orderDataModel.setShipDetail(orders.getShipDetailByShipDetailId()!=null?shipDetailHelper.getShipDetailDataModel(orders.getShipDetailByShipDetailId()):null);
        return  orderDataModel;
    }
    public List<OrderDataModel> getListOrderDataModel(List<Orders> list){
        List<OrderDataModel> listResult=new ArrayList<>();
        for(Orders item:list){
            listResult.add(getOrderDataModel(item));
        }
        return  listResult;
    }
}
