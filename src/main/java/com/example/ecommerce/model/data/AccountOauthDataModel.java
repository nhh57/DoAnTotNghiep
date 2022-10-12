package com.example.ecommerce.model.data;


import com.example.ecommerce.common.Utils;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.ArrayList;
import java.util.List;

@Entity
public class AccountOauthDataModel {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    @Column(name = "username")
    private String userName;
    @Column(name = "password")
    private String passWord;
    @Column(name = "email")
    @Email
    private String Email;
    @Column(name = "full_name")
    private String fullName;
    @Column(name = "phone")
    private String phone;
    @Column(name = "roles")
    private String roles;

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
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

    public List<RolesOauthDataModel> getRoles() {
        try {
            List data = Utils.convertJsonStringToListObject(roles, RolesOauthDataModel[].class);
            return data;
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<RolesOauthDataModel>();
        }
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }
}

