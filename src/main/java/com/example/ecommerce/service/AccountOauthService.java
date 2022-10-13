package com.example.ecommerce.service;
import com.example.ecommerce.model.data.AccountOauthDataModel;

public interface AccountOauthService {
    AccountOauthDataModel getAccountOauth(String  username)  throws Exception;
}
