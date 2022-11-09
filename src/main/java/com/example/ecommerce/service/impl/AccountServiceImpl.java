package com.example.ecommerce.service.impl;

import com.example.ecommerce.common.Utils;
import com.example.ecommerce.model.Account;
import com.example.ecommerce.repository.AccountRepo;
import com.example.ecommerce.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

@Transactional
@Service
public class AccountServiceImpl implements AccountService {
    @Autowired
    private AccountRepo accountRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional(readOnly = true)
    public List<Account> getAllAccount(String keySearch, int isDeleted) throws Exception {
        return accountRepo.getAllAccount(keySearch, isDeleted);
    }

    @Override
    public void createAccount(Account account) throws Exception {
        account.setPassword(passwordEncoder.encode(account.getPassword()));
        accountRepo.createAccount(account.getUsername(),account.getPassword(),account.getEmail(),account.getDateOfBirth(),account.getFullName(),account.getPhone());
    }

    @Override
    @Transactional(readOnly = true)
    public List<Account> getOneAccount(String keySearch) throws Exception {
        return accountRepo.getAccount(keySearch);
    }

    @Override
    public void editProfile(Account account) throws Exception {
        accountRepo.editProfile(account.getFullName(), account.getEmail(), account.getDateOfBirth(), account.getPhone(), account.getId());
    }

    @Override
    public Account findOne(Integer id) throws Exception {
        return accountRepo.getById(id);
    }

    @Override
    public void deleteAccount(Integer id) throws Exception {
        accountRepo.deleteAccount(id);
    }

    @Override
    public void assignRoleForUser(int userId, int roleId) throws Exception {

    }
}
