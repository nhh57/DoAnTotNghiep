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
     * @param keySearch,isDeleted
     * @throws Exception
     */

    List<Account> getAllAccount(String keySearch, int isDeleted) throws Exception;

    /**
     * <p>insertAccount</p>
     * @param account
     * @throws Exception
     */
    void createAccount(Account account) throws Exception;

    /**
     * <p>findOne</p>
     * @param keySearch
     * @return
     */
    List<Account> getOneAccount(String keySearch) throws Exception;
}
