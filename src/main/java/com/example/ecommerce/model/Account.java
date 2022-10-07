package com.example.ecommerce.model;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
public class Account extends BaseEntity implements Serializable {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private int id;
    @Basic
    @Column(name = "username", nullable = true, length = 20)
    private String username;
    @Basic
    @Column(name = "password", nullable = true, length = -1)
    private String password;
    @Basic
    @Email
    @Column(name = "email", nullable = true, length = 255)
    private String email;
    @Basic
    @Column(name = "date_of_birth", nullable = true)
    private Timestamp dateOfBirth;
    @Basic
    @Column(name = "full_name", nullable = true, length = 50)
    private String fullName;
    @Basic
    @Column(name = "phone", nullable = true, length = 20)
    private String phone;
    @Basic
    @Column(name = "ship_detail_id", nullable = true)
    private Integer shipDetailId;
    @Basic
    @Column(name = "cart_id", nullable = true)
    private Integer cartId;
    @Basic
    @Column(name = "is_deleted", nullable = true)
    private Boolean isDeleted;
    @ManyToOne
    @JoinColumn(name = "ship_detail_id", referencedColumnName = "id", insertable = false, updatable = false)
    private ShipDetail shipDetailByShipDetailId;
    @ManyToOne
    @JoinColumn(name = "cart_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Cart cartByCartId;

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

    public Timestamp getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Timestamp dateOfBirth) {
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

    public Boolean getDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }


    public ShipDetail getShipDetailByShipDetailId() {
        return shipDetailByShipDetailId;
    }

    public void setShipDetailByShipDetailId(ShipDetail shipDetailByShipDetailId) {
        this.shipDetailByShipDetailId = shipDetailByShipDetailId;
    }

    public Cart getCartByCartId() {
        return cartByCartId;
    }

    public void setCartByCartId(Cart cartByCartId) {
        this.cartByCartId = cartByCartId;
    }
}
