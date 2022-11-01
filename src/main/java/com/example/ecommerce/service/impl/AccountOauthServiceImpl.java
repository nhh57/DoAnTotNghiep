package com.example.ecommerce.service.impl;

import com.example.ecommerce.jwt.CustomUser;
import com.example.ecommerce.model.data.AccountOauthDataModel;
import com.example.ecommerce.repository.AccountOauthRepo;
import com.example.ecommerce.service.AccountOauthService;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class AccountOauthServiceImpl implements AccountOauthService, UserDetailsService {
    private static Log log = LogFactory.getLog(AccountOauthServiceImpl.class);
    @Autowired
    private AccountOauthRepo oauthRepo;
    @Override
    public AccountOauthDataModel getAccountOauth(String username) throws Exception{
        return oauthRepo.getAccountOauth(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        AccountOauthDataModel accountOauthDataModel = oauthRepo.getAccountOauth(username);
        if(accountOauthDataModel == null){
            log.error("Account not found in the database"+username);
            throw new UsernameNotFoundException("Account not found in the database"+username);
        }else {
            log.info("Account  found in the database"+username);
        }
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        accountOauthDataModel.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        });
        return new CustomUser(accountOauthDataModel,authorities);
    }
}
