package com.example.ecommerce.controller;

import com.example.ecommerce.model.Product;
import com.example.ecommerce.model.data.ProductDataModel;
import com.example.ecommerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ProductController {
    @Autowired
    ProductService productDAO;

    // Get all list product exist
    @GetMapping("/product/getall")
    public ResponseEntity<List<ProductDataModel>> getAll(){
        return ResponseEntity.ok(productDAO.findProductExist());
    }
    // Get all list product
    @GetMapping("/product/getalladmin")
    public ResponseEntity<List<ProductDataModel>> getAllAdmin(){
        return ResponseEntity.ok(productDAO.findAll());
    }
    // Find by name
    @GetMapping("/product/find/{name}")
    public ResponseEntity<List<ProductDataModel>> get(@PathVariable("name") String name) {
        return ResponseEntity.ok(productDAO.findByName(name));
    }
    // Find by price
    @GetMapping("/product/find/{min}/{max}")
    public ResponseEntity<List<ProductDataModel>> get(@PathVariable("min") BigDecimal minPrice, @PathVariable("max") BigDecimal maxPrice) {
        return ResponseEntity.ok(productDAO.findByPrice(minPrice, maxPrice));
    }

    // Find by best selling products
    @GetMapping("/product/find/numberofproduct")
    public ResponseEntity<List<Product>> get(@PathVariable("numberofproduct") Integer numberOfProduct) {
        return ResponseEntity.ok(productDAO.findByBestSellingProducts(numberOfProduct));
    }

    //Get one product
    @GetMapping("/product/getone/{id}")
    public ResponseEntity<ProductDataModel> getOne(@PathVariable("id") Integer id) {
        if (!productDAO.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(productDAO.findById(id));
    }

    // Insert
    @PostMapping("/product/insert")
    public ResponseEntity<ProductDataModel> post(@RequestBody ProductDataModel productDataModel) {
        if (productDAO.existsById(productDataModel.getId())) {
            return ResponseEntity.badRequest().build();
        }
        productDAO.save(productDataModel);
        return ResponseEntity.ok(productDataModel);
    }

    // Update
    @PostMapping("/product/update/{id}")
    public ResponseEntity<ProductDataModel> put(@PathVariable("id") Integer id, @RequestBody ProductDataModel productDataModel) {
        if (!productDAO.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        productDAO.save(productDataModel);
        return ResponseEntity.ok(productDataModel);
    }

    //Delete
    @PostMapping("/product/delete/{id}")
    public ResponseEntity<ProductDataModel> delete(@PathVariable("id") Integer id) {
        ProductDataModel productDataModel= productDAO.findById(id);
        if (!productDAO.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        productDataModel.setIsDeleted(true);
        productDAO.save(productDataModel);
        return ResponseEntity.ok(productDataModel);
    }
}
