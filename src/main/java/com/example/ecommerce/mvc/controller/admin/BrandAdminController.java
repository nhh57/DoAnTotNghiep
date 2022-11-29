package com.example.ecommerce.mvc.controller.admin;



import com.example.ecommerce.model.Brand;
import com.example.ecommerce.model.Product;
import com.example.ecommerce.model.helper.BrandHelper;
import com.example.ecommerce.mvc.dao.SessionDAO;
import com.example.ecommerce.repository.BrandRepo;
import com.example.ecommerce.repository.ProductRepo;
import com.example.ecommerce.repository.RolesDetailRepo;
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
@RequestMapping("mvc/admin/brand")
public class BrandAdminController {
    //    @Autowired
//    HttpServletRequest request;
    @Autowired
    BrandRepo brandDAO;

    @Autowired
    ProductRepo productDAO;

    @Autowired
    SessionDAO session;

    @Autowired
    RolesDetailRepo rolesDetailDAO;

    BrandHelper brandHelper = new BrandHelper();

    @GetMapping("")
    public String index(Model model,
                        @RequestParam("txtSearch") Optional<String> txtSearch,
                        @RequestParam Optional<String> message,
                        @RequestParam("soTrang") Optional<String> soTrangString,
                        @RequestParam("soSanPham") Optional<String> soSanPhamString) {
//        Account account=(Account) session.get("user");
//        if(account!=null){
//            List<RolesDetail> rolesDetailList=rolesDetailDAO.findByAccountId(account.getId());
//            if(!Utils.checkRole(rolesDetailList)){
//                return "redirect:/mvc/loginAdmin";
//            }
//        }
        int soTrang = !soTrangString.isPresent() ? 1 : Integer.parseInt(soTrangString.get());
        int soSanPham = !soSanPhamString.isPresent() ? 6 : Integer.parseInt(soSanPhamString.get());
        int tongSoTrang = txtSearch.isPresent()
                ? brandHelper.getTotalPage(soSanPham, brandDAO.findByBrandName(txtSearch.get()))
                : brandHelper.getTotalPage(soSanPham, brandDAO.findAll());
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
        Page<Brand> pageBrand = txtSearch.isPresent()
                ? brandDAO.findByBrandName(pageable, txtSearch.get())
                : brandDAO.findAll(pageable);
        List<Brand> list = pageBrand.getContent();
        if (message.isPresent()) {
            if (message.get().equals("saved")) {
                model.addAttribute("message", "Lưu thành công!");
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
        model.addAttribute("listBrand", list);

        return "admin/brand";
    }

    @PostMapping("save")
    public String update(@RequestParam("brandName") Optional<String> brandName,
                         @RequestParam("brandId") Optional<String> brandId,
                         @RequestParam("isDeleted") Optional<Boolean> isDeleted) {
        Brand brand = new Brand();
        if (brandId.isPresent()) {
            brand.setId(Integer.parseInt(brandId.get()));
        }
        if (brandName.isPresent()) {
            brand.setBrandName(brandName.get());
            brand.setDeleted(false);
        }
        if(isDeleted.isPresent()){
            brand.setDeleted(isDeleted.get());
        }else{
            brand.setDeleted(false);
        }
        brandDAO.save(brand);
        return "redirect:/mvc/admin/brand?message=saved";
    }

    @GetMapping("delete")
    public String delete(@RequestParam("brandId") Optional<String> brandId) {
        try {
            Integer id = Integer.parseInt(brandId.get());
            List<Product> products=productDAO.findByBrandId(id);
            for(Product product:products){
                product.setDeleted(true);
                productDAO.save(product);
            }
            Brand brand=brandDAO.findById(id).get();
            brand.setDeleted(true);
            brandDAO.save(brand);
            return "redirect:/mvc/admin/brand?message=deleted";
        } catch (Exception e) {
            return "redirect:/mvc/admin/brand?message=deleteFail";
        }
    }
    @GetMapping("revert")
    public String revert(@RequestParam("brandId") Optional<String> brandId) {
        try {
            Integer id = Integer.parseInt(brandId.get());
            List<Product> products=productDAO.findByBrandId(id);
            for(Product product:products){
                product.setDeleted(false);
                productDAO.save(product);
            }
            Brand brand=brandDAO.findById(id).get();
            brand.setDeleted(false);
            brandDAO.save(brand);
            return "redirect:/mvc/admin/brand?message=reverted";
        } catch (Exception e) {
            return "redirect:/mvc/admin/brand?message=revertFail";
        }
    }
}
