package com.example.ecommerce.model.helper;

import com.example.ecommerce.model.Orders;
import com.example.ecommerce.model.data.OrderDataModel;

import java.util.ArrayList;
import java.util.List;

public class OrderHelper {
    FormatHelper formatHelper=new FormatHelper();
    AccountHelper accountHelper=new AccountHelper();
    ShipDetailHelper shipDetailHelper=new ShipDetailHelper();
    public OrderDataModel getOrderDataModel(Orders orders){
        OrderDataModel orderDataModel=new OrderDataModel();
        orderDataModel.setId(orders.getId());
        orderDataModel.setOrderDate(orders.getOrderDate()!=null?formatHelper.formatDateTime(orders.getOrderDate()):null);
        orderDataModel.setNote(orders.getNote());
        orderDataModel.setOrderStatus(orders.getOrderStatus());
        orderDataModel.setTotalMoney(orders.getTotalMoney());
        orderDataModel.setDeliveryDate(orders.getDeliveryDate()!=null?formatHelper.formatDateTime(orders.getDeliveryDate()):null);
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
