package com.example.ecommerce.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "ship_detail")
public class ShipDetail extends BaseEntity implements Serializable {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private int id;
    @Basic
    @Column(name = "phone", nullable = true, length = 20)
    private String phone;

    @Column(name = "province_id", nullable = true, length = -1)
    private int provinceId;
    @Column(name = "province", nullable = true)
    private String province;

    @Column(name = "district_id", nullable = true, length = -1)
    private int districtId;
    @Column(name = "district", nullable = true)
    private String district;
    @Column(name = "ward_id", nullable = true, length = -1)
    private int wardId;

    @Column(name = "ward", nullable = true)
    private String ward;

    @Column(name = "address_more", nullable = true)
    private String addressMore;
    @Basic
    @Column(name = "address", nullable = true, length = -1)
    private String address;


    @Column(name = "full_name", nullable = true)
    private String fullName;
    @Basic
    @Column(name = "account_id", nullable = true, length = -1)
    private int accountId;

    @Basic
    @Column(name = "is_default", nullable = true)
    private Boolean isDefault;
    @Basic
    @Column(name = "is_deleted", nullable = true)
    private Boolean isDeleted;

    @ManyToOne
    @JoinColumn(name = "account_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Account accountByAccountId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Boolean getDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public Account getAccountByAccountId() {
        return accountByAccountId;
    }

    public void setAccountByAccountId(Account accountByAccountId) {
        this.accountByAccountId = accountByAccountId;
    }

    public ShipDetail() {

    }

    public ShipDetail(String phone, String address, String fullName) {
        this.phone = phone;
        this.address = address;
        this.fullName = fullName;
    }

    public Boolean getDefault() {
        return isDefault;
    }

    public void setDefault(Boolean aDefault) {
        isDefault = aDefault;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public int getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(int provinceId) {
        this.provinceId = provinceId;
    }

    public int getDistrictId() {
        return districtId;
    }

    public void setDistrictId(int districtId) {
        this.districtId = districtId;
    }

    public int getWardId() {
        return wardId;
    }

    public void setWardId(int wardId) {
        this.wardId = wardId;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getWard() {
        return ward;
    }

    public void setWard(String ward) {
        this.ward = ward;
    }

    public String getAddressMore() {
        return addressMore;
    }

    public void setAddressMore(String addressMore) {
        this.addressMore = addressMore;
    }

    public ShipDetail(String phone, String address, String fullName, int accountId, Boolean isDefault , Boolean isDeleted) {
        this.phone = phone;
        this.address = address;
        this.fullName = fullName;
        this.accountId = accountId;
        this.isDefault = isDefault;
        this.isDeleted = isDeleted;
    }
}
