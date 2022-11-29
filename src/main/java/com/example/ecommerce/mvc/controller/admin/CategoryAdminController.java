package com.example.ecommerce.mvc.controller.admin;



import com.example.ecommerce.common.Utils;
import com.example.ecommerce.model.Account;
import com.example.ecommerce.model.Categories;
import com.example.ecommerce.model.Product;
import com.example.ecommerce.model.RolesDetail;
import com.example.ecommerce.model.helper.CategoriesHelper;
import com.example.ecommerce.mvc.dao.SessionDAO;
import com.example.ecommerce.repository.CategoriesRepo;
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

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("mvc/admin/category")
public class CategoryAdminController {
//    @Autowired
//    HttpServletRequest request;
    @Autowired
    CategoriesRepo categoryDAO;

    @Autowired
    ProductRepo productDAO;

    @Autowired
    SessionDAO session;

    @Autowired
    RolesDetailRepo rolesDetailDAO;

    CategoriesHelper categoryHelper = new CategoriesHelper();

    @GetMapping("")
    public String index(Model model,
                        @RequestParam Optional<String> message,
                        @RequestParam("soTrang") Optional<String> soTrangString,
                        @RequestParam("soSanPham") Optional<String> soSanPhamString,
                        @RequestParam("txtSearch") Optional<String> txtSearch) {
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
                ? categoryHelper.getTotalPage(soSanPham, categoryDAO.findByCategoryName(txtSearch.get()))
                : categoryHelper.getTotalPage(soSanPham, categoryDAO.findAll());
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
        Page<Categories> pageCategory = txtSearch.isPresent()
                ? categoryDAO.findByCategoryName(pageable, txtSearch.get())
                : categoryDAO.findAll(pageable);
        List<Categories> list = pageCategory.getContent();
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
        model.addAttribute("listCategory", list);

        return "admin/category";
    }

    @PostMapping("save")
    public String update(@RequestParam("categoryName") Optional<String> categoryName,
                         @RequestParam("categoryId") Optional<String> categoryId,
                         @RequestParam("isDeleted") Optional<Boolean> isDeleted) {
        Categories category = new Categories();
        if (categoryId.isPresent()) {
            category.setId(Integer.parseInt(categoryId.get()));
        }
        if (categoryName.isPresent()) {
            category.setCategoryName(categoryName.get());
            category.setDeleted(false);
        }
        if(isDeleted.isPresent()){
            category.setDeleted(isDeleted.get());
        }else{
            category.setDeleted(false);
        }
        categoryDAO.save(category);
        return "redirect:/mvc/admin/category?message=saved";
    }

    @GetMapping("delete")
    public String delete(@RequestParam("categoryId") Optional<String> categoryId) {
        try {
            Integer id = Integer.parseInt(categoryId.get());
            List<Product> products=productDAO.findByCategoryId(id);
            for(Product product:products){
                product.setDeleted(true);
                productDAO.save(product);
            }
            Categories categories=categoryDAO.findById(id).get();
            categories.setDeleted(true);
            categoryDAO.save(categories);
            return "redirect:/mvc/admin/category?message=deleted";
        } catch (Exception e) {
            return "redirect:/mvc/admin/category?message=deleteFail";
        }
    }
    @GetMapping("revert")
    public String revert(@RequestParam("categoryId") Optional<String> categoryId) {
        try {
            Integer id = Integer.parseInt(categoryId.get());
            List<Product> products=productDAO.findByCategoryId(id);
            for(Product product:products){
                product.setDeleted(false);
                productDAO.save(product);
            }
            Categories categories=categoryDAO.findById(id).get();
            categories.setDeleted(false);
            categoryDAO.save(categories);
            return "redirect:/mvc/admin/category?message=reverted";
        } catch (Exception e) {
            return "redirect:/mvc/admin/category?message=revertFail";
        }
    }
}
