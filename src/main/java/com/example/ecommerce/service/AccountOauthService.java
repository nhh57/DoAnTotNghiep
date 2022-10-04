package com.example.ecommerce.service;
import com.example.ecommerce.model.AccountOauthDataModel;

public interface AccountOauthService {
    AccountOauthDataModel getAccountOauth(String username) throws Exception;
}
