package com.example.ecommerce.model.helper;
import com.example.ecommerce.model.OdersDetail;
import com.example.ecommerce.model.data.OrderDetailDataModel;

import java.util.ArrayList;
import java.util.List;

public class OrderDetailHelper {

    ProductHelper productHelper=new ProductHelper();
    OrderHelper orderHelper=new OrderHelper();
    public OrderDetailDataModel getOrderDetailDataModel(OdersDetail ordersDetail){
        OrderDetailDataModel orderDetailDataModel=new OrderDetailDataModel();
        orderDetailDataModel.setId(ordersDetail.getId());
        orderDetailDataModel.setOrderId(ordersDetail.getOrderId());
        orderDetailDataModel.setProductId(ordersDetail.getProductId());
        orderDetailDataModel.setAmount(ordersDetail.getAmount());
        orderDetailDataModel.setPrice(ordersDetail.getPrice());
        orderDetailDataModel.setIsDeleted(ordersDetail.getDeleted());
        orderDetailDataModel.setProducts(productHelper.getProductDataModel(ordersDetail.getProductByProductId()));
        return orderDetailDataModel;
    }
    public List<OrderDetailDataModel> getListOrderDetailDataModel(List<OdersDetail> list){
        List<OrderDetailDataModel> listResult=new ArrayList<>();
        for(OdersDetail item:list){
            listResult.add(getOrderDetailDataModel(item));
        }
        return  listResult;
    }
    public OdersDetail getOrderDetail(OrderDetailDataModel orderDetailDataModel){
        OdersDetail odersDetail=new OdersDetail();
        odersDetail.setId(orderDetailDataModel.getId());
        odersDetail.setOrderId(odersDetail.getOrderId());
        odersDetail.setProductId(odersDetail.getProductId());
        odersDetail.setAmount(odersDetail.getAmount());
        odersDetail.setPrice(odersDetail.getPrice());
        odersDetail.setDeleted(odersDetail.getDeleted()==null?false:odersDetail.getDeleted());
        return odersDetail;
    }
}
