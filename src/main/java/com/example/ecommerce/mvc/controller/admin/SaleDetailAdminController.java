package com.example.ecommerce.mvc.controller.admin;

import com.example.ecommerce.common.Utils;
import com.example.ecommerce.model.Product;
import com.example.ecommerce.model.SaleDetail;
import com.example.ecommerce.model.helper.SaleDetailHelper;
import com.example.ecommerce.repository.ProductRepo;
import com.example.ecommerce.repository.SaleDetailRepo;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("mvc/admin/sale/sale-detail")
public class SaleDetailAdminController {
    @Autowired
    SaleDetailRepo saleDetailRepo;

    @Autowired
    ProductRepo productDAO;

    SaleDetailHelper saleDetailHelper=new SaleDetailHelper();

    @GetMapping("")
    public String saleDetail(Model model,
                             @RequestParam("saleId") Optional<String> saleIdString,
                             @RequestParam Optional<String> message,
                             @RequestParam("soTrang") Optional<String> soTrangString,
                             @RequestParam("soSanPham") Optional<String> soSanPhamString,
                             @RequestParam("txtSearch") Optional<String> txtSearch){
        if(!saleIdString.isPresent() || !Utils.checkIsNumber(saleIdString.get())){
            return  "customer/404";
        }
        int saleId=Integer.parseInt(saleIdString.get());
        int soTrang = !soTrangString.isPresent() ? 1 : Integer.parseInt(soTrangString.get());
        int soSanPham = !soSanPhamString.isPresent() ? 6 : Integer.parseInt(soSanPhamString.get());
        int tongSoTrang = txtSearch.isPresent()
                ? saleDetailHelper.getTotalPage(soSanPham, saleDetailRepo.findAllBySaleIdAndProductName(saleId,txtSearch.get()))
                : saleDetailHelper.getTotalPage(soSanPham, saleDetailRepo.findAllBySaleId(saleId));
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
        Page<SaleDetail> pageSaleDetail = txtSearch.isPresent()
                ? saleDetailRepo.findAllBySaleIdAndProductName(pageable,saleId, txtSearch.get())
                : saleDetailRepo.findAllBySaleId(pageable,saleId);
        List<SaleDetail> list = pageSaleDetail.getContent();
        List<Product> listProductSale=productDAO.findProductSale();
        model.addAttribute("listSaleDetail", list);
        model.addAttribute("listProduct", listProductSale);
        model.addAttribute("saleId", saleId);
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
        }
        return "admin/sale-detail";
    }
    @PostMapping("save")
    public ResponseEntity<String> save(@RequestParam("saleId") Integer saleId,
                                       @RequestParam("productId") Integer productId){
        try{
            SaleDetail saleDetail=saleDetailRepo.findBySaleIdAndProductId(saleId,productId);
            if(saleDetail==null){
                SaleDetail saleDetailSave=new SaleDetail();
                saleDetailSave.setSaleId(saleId);
                saleDetailSave.setProductId(productId);
                saleDetailSave.setDiscountSale(0);
                Product product=productDAO.findById(productId).get();
                saleDetailSave.setDiscountOld(product.getDiscount());
                saleDetailRepo.save(saleDetailSave);
            }
            JSONObject json = new JSONObject();
            json.put("status", "Thành công");
            return ResponseEntity.ok(String.valueOf(json));
        }catch (Exception e){
            return ResponseEntity.ok("Thất bại");
        }
    }

    @PostMapping("change-discount")
    public ResponseEntity<String> changeDiscount(@RequestParam("id") Integer id,
                                                 @RequestParam("discountSale") Integer discountSale){
        try{
            SaleDetail saleDetail=saleDetailRepo.findById(id).get();
            saleDetail.setDiscountSale(discountSale);
            saleDetailRepo.save(saleDetail);
            JSONObject json = new JSONObject();
            json.put("status", "Thành công");
            return ResponseEntity.ok(String.valueOf(json));
        }catch (Exception e){
            return ResponseEntity.ok("Thất bại");
        }
    }

    @PostMapping("remove")
    public ResponseEntity<String> remove(@RequestParam("id") Integer id){
        try{
            saleDetailRepo.deleteById(id);
            JSONObject json = new JSONObject();
            json.put("status", "Thành công");
            return ResponseEntity.ok(String.valueOf(json));
        }catch (Exception e){
            return ResponseEntity.ok("Thất bại");
        }
    }
    @PostMapping("remove-all")
    public ResponseEntity<String> removeAll(@RequestParam("saleId") Integer saleId){
        try{
            List<SaleDetail> list=saleDetailRepo.findAllBySaleId(saleId);
            for(SaleDetail saleDetail:list){
                saleDetailRepo.delete(saleDetail);
            }
            JSONObject json = new JSONObject();
            json.put("status", "Thành công");
            return ResponseEntity.ok(String.valueOf(json));
        }catch (Exception e){
            return ResponseEntity.ok("Thất bại");
        }
    }
}
