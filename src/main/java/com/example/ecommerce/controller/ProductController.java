package com.example.ecommerce.controller;

import com.example.ecommerce.model.Product;
import com.example.ecommerce.repository.ProductDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ProductController {
    @Autowired
    ProductDAO productDAO;

    // Get all list product
    @GetMapping("/product/getall")
    public ResponseEntity<List<Product>> getAll(){
        return ResponseEntity.ok(productDAO.findAll());
    }

    // Find by name
    @GetMapping("/product/find/{name}")
    public ResponseEntity<List<Product>> get(@PathVariable("name") String name) {
        return ResponseEntity.ok(productDAO.findByName(name));
    }
    // Find by price
    @GetMapping("/product/find/{min}/{max}")
    public ResponseEntity<List<Product>> get(@PathVariable("min") Integer minPrice,@PathVariable("max") Integer maxPrice) {
        return ResponseEntity.ok(productDAO.findByPrice(minPrice, maxPrice));
    }

    // Find by best selling products
    @GetMapping("/product/find/numberofproduct")
    public ResponseEntity<List<Product>> get(@PathVariable("numberofproduct") Integer numberOfProduct) {
        return ResponseEntity.ok(productDAO.findByBestSellingProducts(numberOfProduct));
    }

    //Get one product
    @GetMapping("/product/getone/{id}")
    public ResponseEntity<Product> getOne(@PathVariable("id") Integer id) {
        if (!productDAO.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(productDAO.findById(id).get());
    }

    // Insert
    @PostMapping("/product/insert")
    public ResponseEntity<Product> post(@RequestBody Product product) {
        if (productDAO.existsById(product.getId())) {
            return ResponseEntity.badRequest().build();
        }
        productDAO.save(product);
        return ResponseEntity.ok(product);
    }

    // Update
    @PutMapping("product/update/{id}")
    public ResponseEntity<Product> put(@PathVariable("id") Integer id, @RequestBody Product product) {
        if (!productDAO.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        productDAO.save(product);
        return ResponseEntity.ok(product);
    }

    //Delete
    @DeleteMapping("product/update/{id}")
    public ResponseEntity<Product> delete(@PathVariable("id") Integer id) {
        Product product= new Product();
        if (!productDAO.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        product.setDeleted(true);
        productDAO.save(product);
        return ResponseEntity.ok(product);
    }
}
