package com.example.ecommerce.model.result;

import com.example.ecommerce.model.Orders;
import com.example.ecommerce.model.data.OrderDataModel;
import com.example.ecommerce.model.data.OrderDetailDataModel;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OrderResult {
    OrderDataModel orders;
    List<OrderDetailDataModel> listOrderDetail;
}
