package com.example.ecommerce.mvc.controller.admin;

import com.example.ecommerce.model.Account;
import com.example.ecommerce.model.Statistical;
import com.example.ecommerce.mvc.dao.SessionDAO;
import com.example.ecommerce.mvc.model.Report;
import com.example.ecommerce.mvc.model.ReportResult;
import com.example.ecommerce.repository.StatisticalRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Controller
@RequestMapping("mvc/admin/report")
public class ReportAdminController {
    @Autowired
    StatisticalRepo statisticalRepo;

    @Autowired
    SessionDAO session;

    @GetMapping("")
    public String getReport(Model model, @RequestParam("year") Optional<String> year){
        Account account=(Account) session.get("admin");
        if(account==null){
            return "redirect:/mvc/admin/login?error=errorNoLogin&urlReturn=report";
        }
        model.addAttribute("admin",account);
        ReportResult reportResult=new ReportResult();
        List<Report> listReport=new ArrayList<>();
        for(int i=1;i<=12;i++){
            Report report=new Report();
            report.setName("Tháng "+i);
            Statistical statistical=statisticalRepo.getStatistical(year.isPresent() ? year.get() : "2022",String.valueOf(i));
            report.setValue(statistical == null ? 0 :statistical.getValue());
            listReport.add(report);
        }
        reportResult.setListReport(listReport);
        Statistical statistical=statisticalRepo.getOldestYear();
        reportResult.setOldestYear(statistical == null ? 2022 : statistical.getValue());
        reportResult.setNameChart(year.isPresent() ? "Doanh thu năm "+ year.get() : "Doanh thu năm 2022");
        model.addAttribute("report",reportResult);
        model.addAttribute("thisYear",year.isPresent() ? Integer.parseInt(year.get()) : 2022);
        return "admin/report";
    }
}
