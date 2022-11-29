package com.example.ecommerce.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "orders")
public class Orders extends BaseEntity implements Serializable {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private int id;

    @Column(name = "order_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date orderDate;

    @Column(name = "note", nullable = true, length = -1)
    private String note;

    @Column(name = "order_status", nullable = true, length = -1)
    private String orderStatus;

    @Column(name = "total_money", nullable = true, precision = 0)
    private BigDecimal totalMoney;

    @Column(name = "delivery_date", nullable = true)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date deliveryDate;

    @Column(name = "account_id", nullable = true)
    private Integer accountId;

    @Column(name = "ship_detail_id", nullable = true)
    private Integer shipDetailId;

    @Column(name = "is_deleted", nullable = true)
    private Boolean isDeleted;

    @Column(name = "payment_method", nullable = true, length = -1)
    private String paymentMethod;

    @Column(name = "payment_status", nullable = true, length = -1)
    private String paymentStatus;

    @ManyToOne
    @JoinColumn(name = "account_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Account accountByAccountId;

    @ManyToOne
    @JoinColumn(name = "ship_detail_id", referencedColumnName = "id", insertable = false, updatable = false)
    private ShipDetail shipDetailByShipDetailId;

}
