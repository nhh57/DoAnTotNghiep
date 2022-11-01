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
     */
    List<Account> getOneAccount(String keySearch) throws Exception;

    /**
     * <p>editProfile</p>
     * @param account
     * @throws Exception
     */
    void editProfile(Account account) throws Exception;

    /**
     *<p>findOne</p>
     * @param id
     * @throws Exception
     */
    Account findOne(Integer id) throws Exception;

    /**
     * <p>deleteAccount</p>
     * @param id
     * @throws Exception
     */
    void deleteAccount(Integer id) throws Exception;

    /**
     * <p>assignRoleForUser</p>
     * @throws Exception
     */
    void assignRoleForUser(int userId, int roleId) throws Exception;
}
