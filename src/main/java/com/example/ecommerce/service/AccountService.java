package com.example.ecommerce.service;

import com.example.ecommerce.model.Account;

import java.util.List;

/**
 * @author hainh
 */
public interface AccountService {
    /**
     *
     * <p>getAllAccount</p>
     * @throws Exception
     */

    List<Account> getAllAccount(String username, int isDeleted) throws Exception;
}
