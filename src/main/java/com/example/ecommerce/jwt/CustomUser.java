package com.example.ecommerce.jwt;

import com.example.ecommerce.model.data.AccountOauthDataModel;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.List;


@Data
public class CustomUser implements UserDetails {

    private static final long serialVersionUID = 1L;

    private AccountOauthDataModel account;

    private List<? extends GrantedAuthority> authorities;

    public CustomUser (AccountOauthDataModel account, List<? extends GrantedAuthority> authorities) {
        this.account = account;
        this.authorities = authorities;
    }

    @Override
    public List<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return account.getPassWord();
    }

    @Override
    public String getUsername() {
        return account.getUserName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }


    public AccountOauthDataModel getAccount() {
        return account;
    }

    public void setAccount(AccountOauthDataModel account) {
        this.account = account;
    }

    public void setAuthorities(List<? extends GrantedAuthority> authorities) {
        this.authorities = authorities;
    }
}
