package com.example.ecommerce.mvc.helper;

import com.example.ecommerce.model.Account;

import java.util.List;

public class RegisterHelper {
    public boolean checkAccountExist(String username, List<Account> list) {
        boolean check=false;
        for (Account account : list) {
            if (username.equalsIgnoreCase(account.getUsername())) {
                check=true;
                break;
            }
        }
        return check;
    }
    public boolean checkPhoneExist(String phone, List<Account> list) {
        boolean check=false;
        for (Account account : list) {
            if (phone.equalsIgnoreCase(account.getPhone())) {
                check=true;
                break;
            }
        }
        return check;
    }
    public boolean checkEmailExist(String email, List<Account> list) {
        boolean check=false;
        for (Account account : list) {
            if (email.equalsIgnoreCase(account.getEmail())) {
                check=true;
                break;
            }
        }
        return check;
    }
}
