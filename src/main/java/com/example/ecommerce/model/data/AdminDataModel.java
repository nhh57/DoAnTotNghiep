package com.example.ecommerce.model.data;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.math.BigDecimal;
import java.util.Date;

@Entity
public class AdminDataModel {
    @javax.persistence.Id
    @Column(name = "report_day")
    private Date reportDay;
    @Column(name = "total_price")
    private BigDecimal totalPrice;

    public Date getReportDay() {
        return reportDay;
    }

    public void setReportDay(Date reportDay) {
        this.reportDay = reportDay;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }
}
