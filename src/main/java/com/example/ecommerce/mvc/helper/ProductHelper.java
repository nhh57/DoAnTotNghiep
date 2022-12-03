package com.example.ecommerce.mvc.helper;

import com.example.ecommerce.model.Product;
import com.example.ecommerce.mvc.model.ProductMVCResult;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class ProductHelper {
    public int getTotalPage(int soSanPham, List<Product> list) {
        int tongSoSanPham = list.size();
        int tongSoTrang = 1;
        float tempFloat = (float) tongSoSanPham / soSanPham;
        int tempInt = (int) tempFloat;
        if (tempFloat - tempInt > 0) {
            tongSoTrang = tempInt + 1;
        } else {
            tongSoTrang = tempInt;
        }
        return tongSoTrang;
    }

    private Path getPath(String filename) {
        File dir = Paths.get("src/main/resources/static/assets/img/products").toFile();
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return Paths.get(dir.getAbsolutePath(), filename);
    }

    public String save(MultipartFile file) {
//        String name = System.currentTimeMillis() + file.getOriginalFilename();
//        String filename = Integer.toHexString(name.hashCode()) + name.substring(name.lastIndexOf("."));
        String filename =file.getOriginalFilename();
        Path path = this.getPath(filename);
        try {
            // upload file to server folder path
            file.transferTo(path);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return filename;
    }
    public String uploadImage(HttpServletRequest req) throws IOException, ServletException {
        // đường dẫn thư mục tính từ gốc của website
        File dir = new File(req.getServletContext().getRealPath("/assets/img/products"));
        if (!dir.exists()) { // tạo nếu chưa tồn tại
            dir.mkdirs();
        }
        // lưu các file upload vào thư mục sp
        Part photo = req.getPart("images"); // file hình
        String filePath = photo.getSubmittedFileName();
        Path p = Paths.get(filePath); // creates a Path object
        String tenHinhAnh = p.getFileName().toString();
        File photoFile = new File(dir, filePath);
        if (!photoFile.exists()) {
            photo.write(photoFile.getAbsolutePath());
        }
        return tenHinhAnh;
    }
    public List<ProductMVCResult> getListProductMVCResult(List<Product> list) {
        List<ProductMVCResult> listProductMVCResult = new ArrayList<>();
        for (Product item : list) {
            ProductMVCResult productMVCResult = new ProductMVCResult();
            productMVCResult.setProduct(item);
            if(item.getPrice()*(100-item.getDiscount())/100 <= 2000000){
                productMVCResult.setClassPrice("price1");
            }else if(item.getPrice()*(100-item.getDiscount())/100 <= 3000000){
                productMVCResult.setClassPrice("price2");
            }else if(item.getPrice()*(100-item.getDiscount())/100 <= 4000000){
                productMVCResult.setClassPrice("price3");
            }else if(item.getPrice()*(100-item.getDiscount())/100 > 4000000){
                productMVCResult.setClassPrice("price4");
            }
            listProductMVCResult.add(productMVCResult);
        }
        return listProductMVCResult;
    }
}
