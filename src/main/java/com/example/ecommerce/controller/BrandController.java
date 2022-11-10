package com.example.ecommerce.controller;

import com.example.ecommerce.model.Brand;
import com.example.ecommerce.model.data.BrandDataModel;
import com.example.ecommerce.repository.BrandRepo;
import com.example.ecommerce.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/brand")
public class BrandController {
    @Autowired
    BrandService brandService;

    // Get all list brand
    @GetMapping("/free/get-all/{page}/{size}")
    public ResponseEntity<List<BrandDataModel>> getAll(@PathVariable("page") Integer soTrang,
                                                       @PathVariable("size") Integer soSanPham){

        return ResponseEntity.ok(brandService.findAll(soTrang,soSanPham));
    }


    @GetMapping("/free/get-all-admin/{page}/{size}")
    public ResponseEntity<List<BrandDataModel>> getAllAdmin(@PathVariable("page") Integer soTrang,
                                                       @PathVariable("size") Integer soSanPham){

        return ResponseEntity.ok(brandService.findAllAdmin(soTrang,soSanPham));
    }

    // Insert
    @PostMapping("/crud/save")
    public ResponseEntity<BrandDataModel> save(@RequestBody BrandDataModel brandDataModel) {
        return ResponseEntity.ok(brandService.save(brandDataModel));
    }

    //Delete
    @PostMapping("/crud/delete/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(brandService.delete(id));
    }
}
