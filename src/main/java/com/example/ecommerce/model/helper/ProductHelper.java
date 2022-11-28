package com.example.ecommerce.model.helper;

import com.example.ecommerce.model.Product;
import com.example.ecommerce.model.data.ProductDataModel;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class ProductHelper {
    CategoriesHelper categoriesHelper=new CategoriesHelper();
    BrandHelper brandHelper=new BrandHelper();
    public Product getProduct(ProductDataModel productDataModel){
        Product product=new Product();
        product.setId(productDataModel.getId());
        product.setProductName(productDataModel.getProductName());
        product.setImages(productDataModel.getImage());
        product.setNote(productDataModel.getNote());
        product.setPrice(productDataModel.getPrice());
        product.setDiscount(productDataModel.getDiscount());
        product.setNumberOfSale(productDataModel.getNumberOfSale());
        product.setBrandId(productDataModel.getBrandId());
        product.setCategoryId(productDataModel.getCategoryId());
        product.setDeleted(productDataModel.getIsDeleted()!=null?productDataModel.getIsDeleted():false);
        return product;
    }
    public ProductDataModel getProductDataModel(Product product){
        ProductDataModel productDataModel=new ProductDataModel();
        productDataModel.setId(product.getId());
        productDataModel.setProductName(product.getProductName());
        productDataModel.setImage(product.getImages());
        productDataModel.setNote(product.getNote());
        productDataModel.setPrice(product.getPrice());
        productDataModel.setDiscount(product.getDiscount());
        productDataModel.setPromotionPrice(getPromotionPrice(product.getPrice(),product.getDiscount()));
        productDataModel.setNumberOfSale(product.getNumberOfSale());
        productDataModel.setBrandId(product.getBrandId());
        productDataModel.setCategoryId(product.getCategoryId());
        if(product.getCategoriesByCategoryId()!=null){
            productDataModel.setCategories(categoriesHelper.getCategoriesDataModel(product.getCategoriesByCategoryId()));
        }
        if(product.getBrandByBrandId()!=null){
            productDataModel.setBrand(brandHelper.getBrandDataModel(product.getBrandByBrandId()));
        }

        productDataModel.setIsDeleted(product.getDeleted()==null?false:product.getDeleted());
        return productDataModel;
    }
    public List<ProductDataModel> getListProductDataModel(List<Product> listProduct){
        List<ProductDataModel> listProductDataModel=new ArrayList<>();
        for(Product product:listProduct){
            listProductDataModel.add(getProductDataModel(product));
        }
        return listProductDataModel;
    }
    public Double getPromotionPrice(Integer price, Integer discount){
        if(price!=null){
            return price.doubleValue()*discount/100;
        }else{
            return null;
        }

    }
    public int getTotalPage(int soSanPham,int tongSoSanPham) {
        int tongSoTrang = 1;
        double tempDouble = (double) tongSoSanPham / soSanPham;
        int tempInt = (int) tempDouble;
        if (tempDouble - tempInt > 0) {
            tongSoTrang = tempInt + 1;
        } else {
            tongSoTrang = tempInt;
        }
        return tongSoTrang;
    }

    public int getTotalPage2(int soSanPham, List<Product> list) {
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
    public String saveFile(MultipartFile file) {
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

}
