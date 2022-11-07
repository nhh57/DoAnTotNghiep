package com.example.ecommerce.model.data;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderDataModel {
    private int id;
    private String orderDate;
    private String note;
    private String orderStatus;
    private Integer totalMoney;
    private String deliveryDate;
    private Integer accountId;
    private Integer shipDetailId;
    private String paymentMethod;
    private Boolean isDeleted;
    private AccountInforModel account;
    private ShipDetailDataModel shipDetail;
}
