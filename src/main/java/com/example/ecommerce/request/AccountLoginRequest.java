package com.example.ecommerce.request;

import javax.validation.constraints.NotEmpty;

public class AccountLoginRequest {
    @NotEmpty(message = "Tài khoản không đúng")
    private String username;
    @NotEmpty(message = "Mật khẩu không đúng")
    private String password;

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
