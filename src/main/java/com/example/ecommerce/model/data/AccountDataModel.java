package com.example.ecommerce.model.data;


import com.example.ecommerce.model.BaseEntity;

import javax.persistence.*;
import java.io.Serializable;

@Entity

public class AccountDataModel extends BaseEntity implements Serializable {
    @javax.persistence.Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;

    @Column(name = "username")
    private String username;

    @Column(name = "category_id")
    private int categoryId;

    @Column(name = "card_status")
    private int cardStatus;

    @Column(name = "ship_detaild")
    private int shipDetaild;

    public AccountDataModel() {

    }

    public AccountDataModel(int id, String username, int categoryId, int cardStatus, int shipDetaild) {
        Id = id;
        this.username = username;
        this.categoryId = categoryId;
        this.cardStatus = cardStatus;
        this.shipDetaild = shipDetaild;
    }

    public AccountDataModel(int id, String username, int categoryId, int cardStatus) {
        Id = id;
        this.username = username;
        this.categoryId = categoryId;
        this.cardStatus = cardStatus;
    }

    public int getShipDetaild() {
        return shipDetaild;
    }

    public void setShipDetaild(int shipDetaild) {
        this.shipDetaild = shipDetaild;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getCardStatus() {
        return cardStatus;
    }

    public void setCardStatus(int cardStatus) {
        this.cardStatus = cardStatus;
    }
}
