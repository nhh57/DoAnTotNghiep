package com.example.ecommerce.mvc.controller.admin;


import com.example.ecommerce.model.*;
import com.example.ecommerce.model.helper.ProductHelper;
import com.example.ecommerce.model.result.ProductResult;
import com.example.ecommerce.mvc.dao.SessionDAO;
import com.example.ecommerce.mvc.model.ProductAdminResult;
import com.example.ecommerce.repository.*;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("mvc/admin/product")
public class ProductAdminController {
    @Autowired
    ProductRepo productDAO;

    @Autowired
    ProductImageRepo productImageDAO;

    @Autowired
    WarehouseRepo warehouseDAO;

    @Autowired
    CategoriesRepo categoryDAO;

    @Autowired
    BrandRepo brandDAO;

    @Autowired
    SessionDAO session;

    ProductHelper productHelper=new ProductHelper();
    @GetMapping("")
    public String index(Model model,
                        @RequestParam Optional<String> delete,
                        @RequestParam Optional<String> revert,
                        @RequestParam Optional<String> save,
                        @RequestParam("soTrang") Optional<String> soTrangString,
                        @RequestParam("soSanPham") Optional<String> soSanPhamString,
                        @RequestParam("txtSearch") Optional<String> txtSearch) {
        Account admin=(Account) session.get("admin");
        if(admin==null){
            return "redirect:/mvc/admin/login?error=errorNoLogin&urlReturn=product";
        }
        model.addAttribute("admin",admin);
        int soTrang=!soTrangString.isPresent()?1:Integer.parseInt(soTrangString.get());
        int soSanPham=!soSanPhamString.isPresent()?6:Integer.parseInt(soSanPhamString.get());
        int tongSoTrang=txtSearch.isPresent()
                ? productHelper.getTotalPage2(soSanPham, productDAO.findByName(txtSearch.get()))
                : productHelper.getTotalPage2(soSanPham, productDAO.findAll());
        if(soTrang<1){
            soTrang=1;
        }else if(soTrang > tongSoTrang && tongSoTrang > 0){
            soTrang=tongSoTrang;
        }
        model.addAttribute("soTrangHienTai", soTrang);
        model.addAttribute("soSanPhamHienTai", soSanPham);
        model.addAttribute("tongSoTrang", tongSoTrang);
        if(txtSearch.isPresent() && txtSearch.get()!=null){
            model.addAttribute("timKiemHienTai", txtSearch.get());
        }else{
            model.addAttribute("timKiemHienTai", "");
        }
        Pageable pageable = PageRequest.of(soTrang-1, soSanPham);
        Page<Product> pageProduct=txtSearch.isPresent()
                ? productDAO.findByName(pageable,txtSearch.get())
                : productDAO.findAll(pageable);
        List<Product> list=pageProduct.getContent();
        List<ProductAdminResult> listResult=new ArrayList<>();
        for(Product product:list){
            ProductAdminResult productAdminResult=new ProductAdminResult();
            productAdminResult.setProduct(product);
            productAdminResult.setListProductImage(productImageDAO.findByProductId(product.getId()));
            listResult.add(productAdminResult);
        }
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
        model.addAttribute("listProduct",listResult);
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
                       @RequestParam("isDeleted") Optional<Boolean> isDeleted,
                       @RequestParam("warehouseId") Optional<Integer> warehouseId,
                       @RequestParam("amount") Optional<Integer> amount,
                       @RequestParam("images") MultipartFile fileImages,
                       @RequestParam("imageMore") Optional<MultipartFile[]> imageMores,
                       @RequestParam("imagesOld") Optional<String> imagesOld,HttpServletRequest req){
        Account admin=(Account) session.get("admin");
        if(admin==null){
            return "redirect:/mvc/admin/login?error=errorNoLogin&urlReturn=product";
        }
        try{
            //Lưu file vào thư mục của project
            String imagesNameSaved= productHelper.saveFile(fileImages);
            // Upload file lên server tomcat
            productHelper.saveFile(fileImages);
            List<String> listImageMore=new ArrayList<>();
            if(imageMores.isPresent()){
                //Lưu file vào thư mục của project
                productHelper.saveFiles(imageMores.get());
                // Upload file lên server tomcat
                listImageMore= productHelper.uploadImages(req,imagesNameSaved);
            }
            Product product=new Product();
            if(id.isPresent()){
                product.setId(Integer.parseInt(id.get()));
            }
            product.setProductName(productName.isPresent()?productName.get():null);
            product.setPrice(price.isPresent()?Integer.parseInt(price.get()):0);
            product.setDiscount(discount.isPresent()?Integer.parseInt(discount.get()):0);
            product.setNote(note.isPresent()?note.get():null);
            product.setNumberOfSale(0);
            if (isDeleted.isPresent()) {
                product.setDeleted(isDeleted.get());
            } else {
                product.setDeleted(false);
            }
            product.setCategoryId(Integer.parseInt(categoryId.get()));
            product.setBrandId(Integer.parseInt(brandId.get()));
            if(warehouseId.isPresent()){
                product.setWarehouseId(warehouseId.get());
            }
            product.setImages(imagesNameSaved==null || imagesNameSaved.isEmpty()?imagesOld.get():imagesNameSaved);
            Product productSaved=productDAO.save(product);
            //Lưu hình ảnh thêm
            if(listImageMore.size() > 0){
                for(String imageName:listImageMore){
                    if(!productHelper.checkExistProductImageName(productImageDAO.findAll(),imageName)){
                        ProductImage productImage=new ProductImage();
                        productImage.setProductId(productSaved.getId());
                        productImage.setProductImageName(imageName);
                        productImage.setDeleted(false);
                        productImageDAO.save(productImage);
                    }
                }
            }
            // Lưu số lượng vô kho
            Warehouse warehouse=new Warehouse();
            if(warehouseId.isPresent()){
                warehouse.setId(warehouseId.get());
            }
            warehouse.setProductId(productSaved.getId());
            warehouse.setAmount(amount.isPresent()?amount.get():0);
            warehouse.setDeleted(false);
            Warehouse warehouseSaved=warehouseDAO.save(warehouse);
            if(productSaved.getWarehouseId()==null){
                productSaved.setWarehouseId(warehouseSaved.getId());
                productDAO.save(productSaved);
            }
            return "redirect:/mvc/admin/product?save=true";
        }catch (Exception e){
            e.printStackTrace();
            return "redirect:/mvc/admin/product?save=false";
        }
    }

    @GetMapping("delete")
    public String delete(@RequestParam("productId") Optional<String> productId){
        Account admin=(Account) session.get("admin");
        if(admin==null){
            return "redirect:/mvc/admin/login?error=errorNoLogin&urlReturn=product";
        }
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
        Account admin=(Account) session.get("admin");
        if(admin==null){
            return "redirect:/mvc/admin/login?error=errorNoLogin&urlReturn=product";
        }
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

    @PostMapping("product-image/delete")
    public ResponseEntity<String> deleteProductImage(@RequestParam("imageId") Integer imageId) throws JSONException {
       productImageDAO.deleteById(imageId);
        JSONObject json=new JSONObject();
        json.put("status","Thành công!");
        return ResponseEntity.ok(String.valueOf(json));
    }
}
