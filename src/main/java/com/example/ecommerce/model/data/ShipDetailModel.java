package com.example.ecommerce.model.data;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Basic;
import javax.persistence.Column;

@Getter
@Setter
public class ShipDetailModel {
    private int id;
    private String phone;
    private String address;
}
