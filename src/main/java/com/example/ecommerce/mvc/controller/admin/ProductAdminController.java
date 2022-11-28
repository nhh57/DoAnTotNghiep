package com.example.ecommerce.mvc.controller.admin;


import com.example.ecommerce.model.Brand;
import com.example.ecommerce.model.Categories;
import com.example.ecommerce.model.OdersDetail;
import com.example.ecommerce.model.Product;
import com.example.ecommerce.model.helper.ProductHelper;
import com.example.ecommerce.repository.BrandRepo;
import com.example.ecommerce.repository.CategoriesRepo;
import com.example.ecommerce.repository.OrderDetailRepo;
import com.example.ecommerce.repository.ProductRepo;
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
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("mvc/admin/product")
public class ProductAdminController {
//    @Autowired
//    HttpServletRequest request;
    @Autowired
    ProductRepo productDAO;

    @Autowired
    CategoriesRepo categoryDAO;

    @Autowired
    BrandRepo brandDAO;

    @Autowired
    OrderDetailRepo orderDetailDAO;

    ProductHelper productHelper=new ProductHelper();
    @GetMapping("")
    public String index(Model model,
                        @RequestParam Optional<String> delete,
                        @RequestParam Optional<String> revert,
                        @RequestParam Optional<String> save,
                        @RequestParam("soTrang") Optional<String> soTrangString,
                        @RequestParam("soSanPham") Optional<String> soSanPhamString) {
//        if(!(request.isUserInRole("1") || request.isUserInRole("2"))) {
//            return "redirect:/mvc/auth/access/denied";
//        }
        int soTrang=!soTrangString.isPresent()?1:Integer.parseInt(soTrangString.get());
        int soSanPham=!soSanPhamString.isPresent()?6:Integer.parseInt(soSanPhamString.get());
        int tongSoTrang=productHelper.getTotalPage2(soSanPham, productDAO.findAll());
        if(soTrang<1){
            soTrang=1;
        }else if(soTrang>tongSoTrang){
            soTrang=tongSoTrang;
        }
        model.addAttribute("soTrangHienTai", soTrang);
        model.addAttribute("soSanPhamHienTai", soSanPham);
        model.addAttribute("tongSoTrang", tongSoTrang);
        Pageable pageable = PageRequest.of(soTrang-1, soSanPham);
        Page<Product> pageProduct=productDAO.findAll(pageable);
        List<Product> list=pageProduct.getContent();
        if(save.isPresent()) {
            if(save.get().equals("true")){
                model.addAttribute("message","Lưu lại thành công!");
            }else{
                model.addAttribute("message","Lưu lại thất bại! (Bạn cần chính xác thông tin sản phẩm)");
            }
        }
        if(delete.isPresent()) {
            if(delete.get().equals("true")){
                model.addAttribute("message","Xóa thành công!");
            }else{
                model.addAttribute("message","Xóa thất bại!");
            }
        }
        if(revert.isPresent()) {
            if(revert.get().equals("true")){
                model.addAttribute("message","Khôi phục thành công!");
            }else{
                model.addAttribute("message","Khôi phục thất bại!");
            }
        }
        model.addAttribute("listProduct",list);
        //Category
        List<Categories> listCategory=categoryDAO.findAll();
        model.addAttribute("listCategory",listCategory);
        //Brand
        List<Brand> listBrand=brandDAO.findAll();
        model.addAttribute("listBrand",listBrand);
        return "admin/product";
    }

    @PostMapping("save")
    public String save(@RequestParam("id") Optional<String> id,
                       @RequestParam("productName") Optional<String> productName,
                       @RequestParam("price") Optional<String> price,
                       @RequestParam("discount") Optional<String> discount,
                       @RequestParam("note") Optional<String> note,
                       @RequestParam("categoryId") Optional<String> categoryId,
                       @RequestParam("brandId") Optional<String> brandId,
                       @RequestParam("images") MultipartFile fileImages,
                       @RequestParam("imagesOld") Optional<String> imagesOld,HttpServletRequest req){
        try{
            //Lưu file vào thư mục của project
            productHelper.saveFile(fileImages);
            // Upload file lên server tomcat
            String imagesNameSaved= productHelper.uploadImage(req);
            Product product=new Product();
            product.setId(id.isPresent()?Integer.parseInt(id.get()):null);
            product.setProductName(productName.isPresent()?productName.get():null);
            product.setPrice(price.isPresent()?Integer.parseInt(price.get()):null);
            product.setDiscount(discount.isPresent()?Integer.parseInt(discount.get()):null);
            product.setNote(note.isPresent()?note.get():null);
            product.setNumberOfSale(0);
            product.setCategoryId(categoryId.isPresent()?Integer.parseInt(categoryId.get()):null);
            product.setBrandId(brandId.isPresent()?Integer.parseInt(brandId.get()):null);
            product.setImages(imagesNameSaved==null || imagesNameSaved.isEmpty()?imagesOld.get():imagesNameSaved);
            productDAO.save(product);
            return "redirect:/mvc/admin/product?save=true";
        }catch (Exception e){
            e.printStackTrace();
            return "redirect:/mvc/admin/product?save=false";
        }
    }

    @GetMapping("delete")
    public String delete(@RequestParam("productId") Optional<String> productId){
        try{
            Integer id=Integer.parseInt(productId.get());
            Product product=productDAO.findById(id).get();
            product.setDeleted(true);
            productDAO.save(product);
            return "redirect:/mvc/admin/product?delete=true";
        }catch (Exception e){
            return "redirect:/mvc/admin/product?delete=false";
        }
    }
    @GetMapping("revert")
    public String revert(@RequestParam("productId") Optional<String> productId){
        try{
            Integer id=Integer.parseInt(productId.get());
            Product product=productDAO.findById(id).get();
            product.setDeleted(false);
            productDAO.save(product);
            return "redirect:/mvc/admin/product?revert=true";
        }catch (Exception e){
            return "redirect:/mvc/admin/product?revert=false";
        }
    }
}
