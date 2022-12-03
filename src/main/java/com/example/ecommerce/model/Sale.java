package com.example.ecommerce.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalTime;
import java.util.Date;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "sale")
public class Sale {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private int id;

    @Column(name = "sale_date_start")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date saleDateStart;

    @Column(name = "sale_time_start")
    @JsonFormat(pattern = "HH:mm:ss")
    private LocalTime saleTimeStart;

    @Column(name = "sale_date_end")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date saleDateEnd;

    @Column(name = "sale_time_end")
    @JsonFormat(pattern = "HH:mm:ss")
    private LocalTime saleTimeEnd;

    @Column(name = "sale_name", nullable = true, length = -1)
    private String saleName;

    @Column(name = "is_deleted", nullable = true)
    private Boolean isDeleted;

}
