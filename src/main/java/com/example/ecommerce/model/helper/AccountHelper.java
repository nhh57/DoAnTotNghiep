package com.example.ecommerce.model.helper;

import com.example.ecommerce.model.Account;
import com.example.ecommerce.model.data.AccountInforModel;

public class AccountHelper {
    FormatHelper formatHelper=new FormatHelper();
    public AccountInforModel getAccountInforModel(Account account){
        AccountInforModel accountInforModel=new AccountInforModel();
        accountInforModel.setId(account.getId());
        accountInforModel.setPhone(account.getPhone());
        accountInforModel.setEmail(account.getEmail());
        accountInforModel.setFullName(account.getFullName());
        accountInforModel.setDateOfBirth(formatHelper.formatDate(account.getDateOfBirth()));
        accountInforModel.setCartId(account.getCartId());
        return accountInforModel;
    }
}
