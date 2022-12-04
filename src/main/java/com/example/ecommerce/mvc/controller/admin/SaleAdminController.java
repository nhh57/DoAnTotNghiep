package com.example.ecommerce.mvc.controller.admin;

import com.example.ecommerce.common.Utils;
import com.example.ecommerce.model.Sale;
import com.example.ecommerce.model.helper.SaleHelper;
import com.example.ecommerce.mvc.model.SaleResult;
import com.example.ecommerce.repository.SaleDetailRepo;
import com.example.ecommerce.repository.SaleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;


@Controller
@RequestMapping("mvc/admin/sale")
public class SaleAdminController {
    @Autowired
    SaleRepo saleDAO;

    SaleHelper saleHelper=new SaleHelper();

    @GetMapping("")
    public String index(Model model,
                        @RequestParam Optional<String> message,
                        @RequestParam("soTrang") Optional<String> soTrangString,
                        @RequestParam("soSanPham") Optional<String> soSanPhamString,
                        @RequestParam("txtSearch") Optional<String> txtSearch){
        int soTrang = !soTrangString.isPresent() ? 1 : Integer.parseInt(soTrangString.get());
        int soSanPham = !soSanPhamString.isPresent() ? 6 : Integer.parseInt(soSanPhamString.get());
        int tongSoTrang = txtSearch.isPresent()
                ? saleHelper.getTotalPage(soSanPham, saleDAO.findBySaleName(txtSearch.get()))
                : saleHelper.getTotalPage(soSanPham, saleDAO.findAll());
        if (soTrang < 1) {
            soTrang = 1;
        } else if (soTrang > tongSoTrang && tongSoTrang > 0) {
            soTrang = tongSoTrang;
        }
        model.addAttribute("soTrangHienTai", soTrang);
        model.addAttribute("soSanPhamHienTai", soSanPham);
        model.addAttribute("tongSoTrang", tongSoTrang);
        if(txtSearch.isPresent() && txtSearch.get()!=null){
            model.addAttribute("timKiemHienTai", txtSearch.get());
        }else{
            model.addAttribute("timKiemHienTai", "");
        }
        Pageable pageable = PageRequest.of(soTrang - 1, soSanPham);
        Page<Sale> pageSale = txtSearch.isPresent()
                ? saleDAO.findBySaleName(pageable, txtSearch.get())
                : saleDAO.findAll(pageable);
        List<Sale> list = pageSale.getContent();
        List<SaleResult> listSaleResult=saleHelper.getListSaleResult(list);
        model.addAttribute("listSale", listSaleResult);
        if (message.isPresent()) {
            if (message.get().equals("saved")) {
                model.addAttribute("message", "Lưu thành công!");
            }
            if (message.get().equals("saveFail")) {
                model.addAttribute("message", "Lưu thất bại!");
            }
            if (message.get().equals("deleted")) {
                model.addAttribute("message", "Xóa thành công!");
            }
            if (message.get().equals("deleteFail")) {
                model.addAttribute("message", "Xóa thất bại!");
            }
            if (message.get().equals("reverted")) {
                model.addAttribute("message", "Khôi phục thành công!");
            }
            if (message.get().equals("revertFail")) {
                model.addAttribute("message", "Khôi phục thất bại!");
            }
        }
        return "admin/sale";
    }

    @PostMapping("save")
    public String save(@RequestParam("saleId") Optional<Integer> saleId,
                       @RequestParam("dateStartSale") String dateStartSale,
                       @RequestParam("dateEndSale") String dateEndSale,
                       @RequestParam("saleName") String saleName){
        try{
            Sale sale=new Sale();
            if(saleId.isPresent()){
                sale.setId(saleId.get());
            }
            sale.setSaleName(saleName);
            sale.setSaleDateStart(Utils.getDateFromDateTimeLocalString(dateStartSale));
            sale.setSaleTimeStart(Utils.getLocalTimeFromDateTimeLocalString(dateStartSale));
            sale.setSaleDateEnd(Utils.getDateFromDateTimeLocalString(dateEndSale));
            sale.setSaleTimeEnd(Utils.getLocalTimeFromDateTimeLocalString(dateEndSale));
            sale.setIsDeleted(false);
            saleDAO.save(sale);
            return "redirect:/mvc/admin/sale?message=saved";
        }catch (Exception e){
            return "redirect:/mvc/admin/sale?message=saveFail";
        }
    }

    @GetMapping("delete")
    public String delete(@RequestParam("saleId") Optional<String> saleId) {
        try {
            Integer id = Integer.parseInt(saleId.get());
            Sale sale=saleDAO.findById(id).get();
            sale.setIsDeleted(true);
            saleDAO.save(sale);
            return "redirect:/mvc/admin/sale?message=deleted";
        } catch (Exception e) {
            return "redirect:/mvc/admin/sale?message=deleteFail";
        }
    }
    @GetMapping("revert")
    public String revert(@RequestParam("saleId") Optional<String> saleId) {
        try {
            Integer id = Integer.parseInt(saleId.get());
            Sale sale=saleDAO.findById(id).get();
            sale.setIsDeleted(false);
            saleDAO.save(sale);
            return "redirect:/mvc/admin/sale?message=reverted";
        } catch (Exception e) {
            return "redirect:/mvc/admin/sale?message=revertFail";
        }
    }
}
