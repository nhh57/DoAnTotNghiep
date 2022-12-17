package com.example.ecommerce.mvc.controller.admin;

import com.example.ecommerce.common.Utils;
import com.example.ecommerce.model.Account;
import com.example.ecommerce.model.Orders;
import com.example.ecommerce.model.Statistical;
import com.example.ecommerce.mvc.dao.SessionDAO;
import com.example.ecommerce.mvc.excel.ProductExcelExporter;
import com.example.ecommerce.mvc.excel.TimeExcelExporter;
import com.example.ecommerce.mvc.excel.UserExcelExporter;
import com.example.ecommerce.mvc.model.*;
import com.example.ecommerce.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Year;
import java.util.ArrayList;
import java.util.Date;
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

    int numberOfChart = 10;

    @GetMapping("")
    public String getReport(Model model,
                            @RequestParam("column") Optional<Integer> column,
                            @RequestParam("year") Optional<String> year,
                            @RequestParam("sStartDate") Optional<String> sStartDate,
                            @RequestParam("sEndDate") Optional<String> sEndDate,
                            @RequestParam("mYear") Optional<String> mYear,
                            @RequestParam("mMonth") Optional<String> mMonth,
                            @RequestParam("tabPanelIndex") Optional<String> tabPanelIndex,
                            @RequestParam("startDate") Optional<String> startDate,
                            @RequestParam("endDate") Optional<String> endDate,
                            @RequestParam("accountId") Optional<Integer> accountId,
                            @RequestParam("tag") Optional<String> tag) throws ParseException {
        int tagIndex = 1;
        if (tag.isPresent()) {
            if (tag.get().equals("1")) {
                tagIndex = 1;
            } else if (tag.get().equals("2")) {
                tagIndex = 2;
            } else {
                tagIndex = 3;
            }
        }
        Account account = (Account) session.get("admin");
        if (account == null) {
            return "redirect:/mvc/admin/login?error=errorNoLogin&urlReturn=report?tag=" + tagIndex;
        }
        model.addAttribute("tag", tagIndex);
        model.addAttribute("admin", account);
        if (tagIndex == 1) {
            List<ProductReport> listProductReport;
            if (startDate.isPresent() && endDate.isPresent() && !startDate.get().isEmpty() && !endDate.get().isEmpty()) {
                String endDateResult = Utils.plusDateIntoDateString(endDate.get(), 1);
                listProductReport = productReportRepo.getProductReportByDate(startDate.get(), endDateResult);
            } else {
                listProductReport = productReportRepo.getProductReport();
            }
            List<ProductReport> listProductResult = new ArrayList<>();
            for (ProductReport pr : listProductReport) {
                if (pr.getNumberOfSale() != null) {
                    listProductResult.add(pr);
                }
            }
            List<ProductReport> listProductChart = new ArrayList<>();
            if (!listProductResult.isEmpty() && listProductResult.size() > 10) {
                for (int i = 0; i < numberOfChart; i++) {
                    ProductReport item = listProductResult.get(i);
                    listProductChart.add(item);
                }
            } else {
                listProductChart.addAll(listProductResult);
            }
            model.addAttribute("tabPanelIndex", tabPanelIndex.isPresent() ? tabPanelIndex.get() : "tongQuan");
            model.addAttribute("startDate", startDate.isPresent() ? startDate.get() : "");
            model.addAttribute("endDate", endDate.isPresent() ? endDate.get() : "");
            session.set("listProductExcel",listProductResult);
            model.addAttribute("listProduct", listProductResult);
            model.addAttribute("listProductChart", listProductChart);
            return "admin/report-product";
        } else if (tagIndex == 2) {
            List<AccountReport> listAccountReport;
            List<Orders> listOrder = null;
            if (startDate.isPresent() && endDate.isPresent() && !startDate.get().isEmpty() && !endDate.get().isEmpty()) {
                String endDateResult = Utils.plusDateIntoDateString(endDate.get(), 1);
                listAccountReport = accountReportRepo.getAccountReportByDate(startDate.get(), endDateResult);
                if (accountId.isPresent()) {
                    listOrder = orderRepo.findAllByAccountIdAndDate(accountId.get(), startDate.get(), endDateResult);
                }
            } else {
                if (accountId.isPresent()) {
                    listOrder = orderRepo.findAllByAccountId(accountId.get());
                }
                listAccountReport = accountReportRepo.getAccountReport();
            }
            List<AccountReport> listAccountResult = new ArrayList<>();
            for (AccountReport ar : listAccountReport) {
                if (ar.getTotalMoney() != null) {
                    listAccountResult.add(ar);
                }
            }
            List<OrderResult> listOrderResult = new ArrayList<>();
            if (listOrder != null) {
                for (Orders o : listOrder) {
                    OrderResult orderResult = new OrderResult();
                    orderResult.setOrders(o);
                    orderResult.setListOrderDetail(orderDetailRepo.findAllByOrderId(o.getId()));
                    listOrderResult.add(orderResult);
                }
            } else {
                listOrderResult = null;
            }
            List<AccountReport> listAccountChart = new ArrayList<>();
            if (!listAccountResult.isEmpty() && listAccountResult.size() > 10) {
                for (int i = 0; i < numberOfChart; i++) {
                    AccountReport item = listAccountResult.get(i);
                    listAccountChart.add(item);
                }
            } else {
                listAccountChart.addAll(listAccountResult);
            }
            model.addAttribute("tabPanelIndex", tabPanelIndex.isPresent() ? tabPanelIndex.get() : "tongQuan");
            model.addAttribute("listOrderResult", listOrderResult);
            model.addAttribute("startDate", startDate.isPresent() ? startDate.get() : "");
            model.addAttribute("endDate", endDate.isPresent() ? endDate.get() : "");
            model.addAttribute("listAccountChart", listAccountChart);
            model.addAttribute("listAccount", listAccountResult);
            session.set("listUserExcel",listAccountResult);
            return "admin/report-user";
        } else {
            int yearNow = Year.now().getValue();
            if (sStartDate.isPresent() && sEndDate.isPresent()) {
                ReportResult reportResult = new ReportResult();
                List<Report> listReport = new ArrayList<>();
                for (int i = 0; i <= Utils.getDistanceBetweenTwoDateString(sStartDate.get(),sEndDate.get()); i++) {
                    String dateString=Utils.plusDateIntoDateString(sStartDate.get(),i);
                    String yearString=Utils.getYearFromDateString(dateString);
                    String monthString=Utils.getMonthFromDateString(dateString);
                    String dayString=Utils.getDayFromDateString(dateString);
                    Report report = new Report();
                    report.setName(dayString+"/"+monthString+"/"+yearString);
                    Statistical statistical = statisticalRepo.getStatisticalByMonth(yearString, monthString, dayString);
                    report.setValue(statistical == null ? 0 : statistical.getValue());
                    listReport.add(report);
                }
                reportResult.setListReport(listReport);
                Statistical statistical = statisticalRepo.getOldestYear();

                reportResult.setOldestYear(statistical == null ? yearNow : statistical.getValue());
                reportResult.setNameChart("Doanh thu từ " + Utils.formatDateString(sStartDate.get()) + " đến " + Utils.formatDateString(sEndDate.get()));
                model.addAttribute("tabPanelIndex", tabPanelIndex.isPresent() ? tabPanelIndex.get() : "tongQuan");
                model.addAttribute("report", reportResult);
                model.addAttribute("sStartDate", sStartDate.get());
                model.addAttribute("sEndDate", sEndDate.get());
                session.set("listTimeExcel",listReport);
                return "admin/report-stage";
            } else if (mMonth.isPresent() && mYear.isPresent()) {
                ReportResult reportResult = new ReportResult();
                List<Report> listReport = new ArrayList<>();
                for (int i = 1; i <= Utils.getNumberOfDaysInMonth(mYear.get(), mMonth.get()); i++) {
                    Report report = new Report();
                    report.setName("" + i);
                    Statistical statistical = statisticalRepo.getStatisticalByMonth(mYear.get(), mMonth.get(), String.valueOf(i));
                    report.setValue(statistical == null ? 0 : statistical.getValue());
                    listReport.add(report);
                }
                reportResult.setListReport(listReport);
                Statistical statistical = statisticalRepo.getOldestYear();

                reportResult.setOldestYear(statistical == null ? yearNow : statistical.getValue());
                reportResult.setNameChart("Doanh thu tháng " + mMonth.get() + " năm " + mYear.get());
                model.addAttribute("tabPanelIndex", tabPanelIndex.isPresent() ? tabPanelIndex.get() : "tongQuan");
                model.addAttribute("report", reportResult);
                model.addAttribute("mYear", Integer.parseInt(mYear.get()));
                model.addAttribute("mMonth", Integer.parseInt(mMonth.get()));
                session.set("listTimeExcel",listReport);
                return "admin/report-month";
            } else {
                int columnInt = column.isPresent() ? column.get() : 1;
                if (columnInt == 1) {
                    ReportResult reportResult = new ReportResult();
                    List<Report> listReport = new ArrayList<>();
                    for (int i = 1; i <= 12; i++) {
                        Report report = new Report();
                        report.setName("Tháng " + i);
                        Statistical statistical = statisticalRepo.getStatisticalByYear(year.isPresent() ? year.get() : String.valueOf(yearNow), String.valueOf(i));
                        report.setValue(statistical == null ? 0 : statistical.getValue());
                        listReport.add(report);
                    }
                    reportResult.setListReport(listReport);
                    Statistical statistical = statisticalRepo.getOldestYear();

                    reportResult.setOldestYear(statistical == null ? yearNow : statistical.getValue());
                    reportResult.setNameChart(year.isPresent() ? "Doanh thu năm " + year.get() + " theo tháng" : "Doanh thu năm " + yearNow + " theo tháng");
                    model.addAttribute("tabPanelIndex", tabPanelIndex.isPresent() ? tabPanelIndex.get() : "tongQuan");
                    model.addAttribute("report", reportResult);
                    model.addAttribute("thisYear", year.isPresent() ? Integer.parseInt(year.get()) : yearNow);
                    model.addAttribute("column", columnInt);
                    session.set("listTimeExcel",listReport);
                    return "admin/report-year";
                } else {
                    ReportResult reportResult = new ReportResult();
                    List<Report> listReport = new ArrayList<>();
                    int quarterValue = 0;
                    int quarterName = 1;
                    for (int i = 1; i <= 12; i++) {
                        Statistical statistical = statisticalRepo.getStatisticalByYear(year.get(), String.valueOf(i));
                        quarterValue += statistical == null ? 0 : statistical.getValue();
                        if (i % 3 == 0) {
                            Report report = new Report();
                            report.setName("Quý " + quarterName);
                            report.setValue(quarterValue);
                            listReport.add(report);
                            quarterName++;
                            quarterValue = 0;
                        }
                    }
                    reportResult.setListReport(listReport);
                    Statistical statistical = statisticalRepo.getOldestYear();
                    reportResult.setOldestYear(statistical == null ? yearNow : statistical.getValue());
                    reportResult.setNameChart(year.isPresent() ? "Doanh thu năm " + year.get() + " theo quý" : "Doanh thu năm " + yearNow + " theo quý");
                    model.addAttribute("tabPanelIndex", tabPanelIndex.isPresent() ? tabPanelIndex.get() : "tongQuan");
                    model.addAttribute("report", reportResult);
                    model.addAttribute("thisYear", year.isPresent() ? Integer.parseInt(year.get()) : yearNow);
                    model.addAttribute("column", columnInt);
                    session.set("listTimeExcel",listReport);
                    return "admin/report-quarter";
                }
            }
        }
    }

    @GetMapping("product/export-excel")
    public void exportProductToExcel(HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=product_" + currentDateTime + ".xlsx";
        response.setHeader(headerKey, headerValue);

        List<ProductReport> listProductExcel = (List<ProductReport>) session.get("listProductExcel");
        ProductExcelExporter excelExporter = new ProductExcelExporter(listProductExcel);
        excelExporter.export(response);
    }
    @GetMapping("user/export-excel")
    public void exportUserToExcel(HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=user_" + currentDateTime + ".xlsx";
        response.setHeader(headerKey, headerValue);

        List<AccountReport> listUserExcel = (List<AccountReport>) session.get("listUserExcel");
        UserExcelExporter excelExporter = new UserExcelExporter(listUserExcel);
        excelExporter.export(response);
    }
    @GetMapping("time/export-excel")
    public void exportTimeToExcel(HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=time_" + currentDateTime + ".xlsx";
        response.setHeader(headerKey, headerValue);

        List<Report> listTimeExcel = (List<Report>) session.get("listTimeExcel");
        TimeExcelExporter excelExporter = new TimeExcelExporter(listTimeExcel);
        excelExporter.export(response);
    }
}
