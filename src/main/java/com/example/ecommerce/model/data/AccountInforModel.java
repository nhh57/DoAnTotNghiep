package com.example.ecommerce.model.data;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountInforModel {
    private int id;
    private String username;
    private String email;
    private String dateOfBirth;
    private String fullName;
    private String phone;
    private Integer shipDetailId;
    private Integer cartId;
}
