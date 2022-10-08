package com.example.ecommerce.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public class EditProfileRequest {
    @JsonProperty("email")
    private String email;

    @JsonProperty("date_of_birth")
    private String dateOfBirth;

    @JsonProperty("full_name")
    private String fullName;

    @JsonProperty("phone")
    private String phone;


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
}
