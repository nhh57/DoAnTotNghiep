package com.example.ecommerce.mvc.model;

import com.example.ecommerce.model.OdersDetail;
import com.example.ecommerce.model.Orders;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OrderResult {
    Orders orders;
    List<OdersDetail> listOrderDetail;
}
