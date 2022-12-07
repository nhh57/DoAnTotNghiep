package com.example.ecommerce.mvc.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ReportResult {
    private List<Report> listReport;
    private String nameChart;
    private Integer oldestYear;

}
