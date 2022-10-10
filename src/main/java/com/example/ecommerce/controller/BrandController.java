package com.example.ecommerce.controller;

import com.example.ecommerce.model.Brand;
import com.example.ecommerce.repository.BrandRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class BrandController {
    @Autowired
    BrandRepo brandDAO;

    // Get all list brand
    @GetMapping("/brand/getall")
    public ResponseEntity<List<Brand>> getAll(){
        return ResponseEntity.ok(brandDAO.findAll());
    }

    //Get one brand
    @GetMapping("/brand/getone/{id}")
    public ResponseEntity<Brand> getOne(@PathVariable("id") Integer id) {
        if (!brandDAO.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(brandDAO.findById(id).get());
    }

    // Insert
    @PostMapping("/brand/insert")
    public ResponseEntity<Brand> post(@RequestBody Brand brand) {
        if (brandDAO.existsById(brand.getId())) {
            return ResponseEntity.badRequest().build();
        }
        brandDAO.save(brand);
        return ResponseEntity.ok(brand);
    }

    // Update
    @PutMapping("/brand/update/{id}")
    public ResponseEntity<Brand> put(@PathVariable("id") Integer id, @RequestBody Brand brand) {
        if (!brandDAO.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        brandDAO.save(brand);
        return ResponseEntity.ok(brand);
    }

    //Delete
    @DeleteMapping("/brand/update/{id}")
    public ResponseEntity<Brand> delete(@PathVariable("id") Integer id) {
        Brand brand= new Brand();
        if (!brandDAO.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        brand.setDeleted(true);
        brandDAO.save(brand);
        return ResponseEntity.ok(brand);
    }
}
