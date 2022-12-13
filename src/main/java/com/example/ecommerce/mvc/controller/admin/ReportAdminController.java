package com.example.ecommerce.mvc.controller.admin;

import com.example.ecommerce.common.Utils;
import com.example.ecommerce.model.Account;
import com.example.ecommerce.model.Orders;
import com.example.ecommerce.model.Statistical;
import com.example.ecommerce.mvc.dao.SessionDAO;
import com.example.ecommerce.mvc.model.*;
import com.example.ecommerce.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Controller
@RequestMapping("mvc/admin/report")
public class ReportAdminController {
    @Autowired
    StatisticalRepo statisticalRepo;

    @Autowired
    ProductReportRepo productReportRepo;

    @Autowired
    AccountReportRepo accountReportRepo;

    @Autowired
    OrderDetailRepo orderDetailRepo;

    @Autowired
    OrderRepo orderRepo;

    @Autowired
    SessionDAO session;

    @GetMapping("")
    public String getReport(Model model,
                            @RequestParam("year") Optional<String> year,
                            @RequestParam("startDate") Optional<String> startDate,
                            @RequestParam("endDate") Optional<String> endDate,
                            @RequestParam("accountId") Optional<Integer> accountId,
                            @RequestParam("tag") Optional<String> tag) throws ParseException {
        int tagIndex=1;
        if(tag.isPresent()){
            if(tag.get().equals("1")){
                tagIndex=1;
            }else if(tag.get().equals("2")){
                tagIndex=2;
            }
        }
        Account account=(Account) session.get("admin");
        if(account==null){
            return "redirect:/mvc/admin/login?error=errorNoLogin&urlReturn=report?tag="+tagIndex;
        }
        model.addAttribute("tag",tagIndex);
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
        if(tagIndex==1){
            List<ProductReport> listProductReport;
            if(startDate.isPresent() && endDate.isPresent() && !startDate.get().isEmpty() && !endDate.get().isEmpty()){
                String endDateResult=Utils.plusDateIntoDateString(endDate.get(),1);
                listProductReport=productReportRepo.getProductReportByDate(startDate.get(),endDateResult);
            }else{
                listProductReport=productReportRepo.getProductReport();
            }
            List<ProductReport> listResult=new ArrayList<>();
            for(ProductReport pr : listProductReport){
                if(pr.getNumberOfSale()!=null){
                    listResult.add(pr);
                }
            }
            model.addAttribute("startDate",startDate.isPresent() ? startDate.get() : "");
            model.addAttribute("endDate",endDate.isPresent() ? endDate.get() : "");
            model.addAttribute("listProduct",listResult);
            return "admin/report-product";
        }else if(tagIndex==2){
            List<AccountReport> listAccountReport;
            List<Orders> listOrder = null;
            if(startDate.isPresent() && endDate.isPresent() && !startDate.get().isEmpty() && !endDate.get().isEmpty()){
                String endDateResult=Utils.plusDateIntoDateString(endDate.get(),1);
                listAccountReport=accountReportRepo.getAccountReportByDate(startDate.get(),endDateResult);
                if(accountId.isPresent()){
                    listOrder=orderRepo.findAllByAccountIdAndDate(accountId.get(),startDate.get(),endDateResult);
                }
            }else{
                if(accountId.isPresent()){
                    listOrder=orderRepo.findAllByAccountId(accountId.get());
                }
                listAccountReport=accountReportRepo.getAccountReport();
            }
            List<AccountReport> listResult=new ArrayList<>();
            for(AccountReport ar : listAccountReport){
                if(ar.getTotalMoney()!=null){
                    listResult.add(ar);
                }
            }
            List<OrderResult> listOrderResult=new ArrayList<>();
            if(listOrder !=null){
                for(Orders o:listOrder){
                    OrderResult orderResult=new OrderResult();
                    orderResult.setOrders(o);
                    orderResult.setListOrderDetail(orderDetailRepo.findAllByOrderId(o.getId()));
                    listOrderResult.add(orderResult);
                }
            }else{
                listOrderResult=null;
            }
            model.addAttribute("listOrderResult",listOrderResult);
            model.addAttribute("startDate",startDate.isPresent() ? startDate.get() : "");
            model.addAttribute("endDate",endDate.isPresent() ? endDate.get() : "");
            model.addAttribute("listAccount",listResult);
            return "admin/report-user";
        }
        return "admin/report-product";
    }
}
