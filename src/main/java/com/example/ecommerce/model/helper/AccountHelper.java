package com.example.ecommerce.model.helper;

import com.example.ecommerce.common.Utils;
import com.example.ecommerce.model.Account;
import com.example.ecommerce.model.data.AccountInforModel;

import java.util.List;

public class AccountHelper {
    public AccountInforModel getAccountInforModel(Account account){
        AccountInforModel accountInforModel=new AccountInforModel();
        accountInforModel.setId(account.getId());
        accountInforModel.setPhone(account.getPhone());
        accountInforModel.setEmail(account.getEmail());
        accountInforModel.setFullName(account.getFullName());
        accountInforModel.setDateOfBirth(Utils.convertDateToString(account.getDateOfBirth()));
        accountInforModel.setCartId(account.getCartId());
        return accountInforModel;
    }
    public int getTotalPage(int soSanPham, List<Account> list) {
        int tongSoSanPham = list.size();
        int tongSoTrang = 1;
        float tempFloat = (float) tongSoSanPham / soSanPham;
        int tempInt = (int) tempFloat;
        if (tempFloat - tempInt > 0) {
            tongSoTrang = tempInt + 1;
        } else {
            tongSoTrang = tempInt;
        }
        return tongSoTrang;
    }
}
