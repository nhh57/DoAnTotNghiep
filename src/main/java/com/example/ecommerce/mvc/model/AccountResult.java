package com.example.ecommerce.mvc.model;

import com.example.ecommerce.model.Account;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AccountResult {
    Account account;
    List<RoleCustom> roleCustom;
}
