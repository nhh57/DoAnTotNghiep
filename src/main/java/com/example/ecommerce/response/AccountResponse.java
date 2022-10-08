package com.example.ecommerce.response;

import com.example.ecommerce.common.Utils;
import com.example.ecommerce.model.Account;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

public class AccountResponse {
    private int id;

    @JsonProperty("username")
    private String username;

    private String password;

    private String email;
    @JsonProperty("date_of_birth")
    private String dateOfBirth;

    @JsonProperty("full_name")
    private String fullName;

    private String phone;

    @JsonProperty("ship_detail_id")
    private Integer shipDetailId;

    @JsonProperty("cart_id")
    private Integer cartId;

    @JsonProperty("is_deleted")
    private int isDeleted;

    public AccountResponse(Account account) {
        this.id = account.getId();
        this.username = account.getUsername() == null ? "" : account.getUsername();
        this.password = account.getPassword() == null ? "" : account.getPassword();
        this.email = account.getEmail() == null ? "" : account.getEmail();
        this.dateOfBirth = account.getDateOfBirth() == null ? "" : Utils.getDateFormatVN(account.getDateOfBirth());
        this.fullName = account.getFullName() == null ? "" : account.getFullName();
        this.phone = account.getPhone() == null ? "" : account.getPhone();
        this.shipDetailId = account.getShipDetailId();
        this.cartId = account.getCartId();
        this.isDeleted = account.getDeleted() == true ? 1 : 0;
    }

    public AccountResponse() {

    }

    public List<AccountResponse> mapToListResponse(List<Account> list) {
        return list.stream().map(e -> new AccountResponse(e)).collect(Collectors.toList());
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getShipDetailId() {
        return shipDetailId;
    }

    public void setShipDetailId(Integer shipDetailId) {
        this.shipDetailId = shipDetailId;
    }

    public Integer getCartId() {
        return cartId;
    }

    public void setCartId(Integer cartId) {
        this.cartId = cartId;
    }

    public int getDeleted() {
        return isDeleted;
    }

    public void setDeleted(int deleted) {
        isDeleted = deleted;
    }
}
