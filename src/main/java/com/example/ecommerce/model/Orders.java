package com.example.ecommerce.model;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
public class Orders {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private int id;
    @Basic
    @Column(name = "order_date", nullable = true)
    private Timestamp orderDate;
    @Basic
    @Column(name = "note", nullable = true, length = -1)
    private String note;
    @Basic
    @Column(name = "order_status", nullable = true)
    private Integer orderStatus;
    @Basic
    @Column(name = "total_money", nullable = true, precision = 0)
    private Integer totalMoney;
    @Basic
    @Column(name = "delivery_date", nullable = true)
    private Timestamp deliveryDate;
    @Basic
    @Column(name = "account_id", nullable = true)
    private Integer accountId;
    @Basic
    @Column(name = "ship_detail_id", nullable = true)
    private Integer shipDetailId;
    @Basic
    @Column(name = "is_deleted", nullable = true)
    private Boolean isDeleted;
    @ManyToOne
    @JoinColumn(name = "account_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Account accountByAccountId;
    @ManyToOne
    @JoinColumn(name = "ship_detail_id", referencedColumnName = "id", insertable = false, updatable = false)
    private ShipDetail shipDetailByShipDetailId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Timestamp getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Timestamp orderDate) {
        this.orderDate = orderDate;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Integer getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Integer getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(Integer totalMoney) {
        this.totalMoney = totalMoney;
    }

    public Timestamp getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(Timestamp deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public Integer getShipDetailId() {
        return shipDetailId;
    }

    public void setShipDetailId(Integer shipDetailId) {
        this.shipDetailId = shipDetailId;
    }

    public Boolean getDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }

    public Account getAccountByAccountId() {
        return accountByAccountId;
    }

    public void setAccountByAccountId(Account accountByAccountId) {
        this.accountByAccountId = accountByAccountId;
    }

    public ShipDetail getShipDetailByShipDetailId() {
        return shipDetailByShipDetailId;
    }

    public void setShipDetailByShipDetailId(ShipDetail shipDetailByShipDetailId) {
        this.shipDetailByShipDetailId = shipDetailByShipDetailId;
    }
}
