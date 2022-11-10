package com.example.ecommerce.controller;

import com.example.ecommerce.model.Categories;
import com.example.ecommerce.model.data.CategoriesDataModel;
import com.example.ecommerce.repository.CategoriesRepo;
import com.example.ecommerce.service.CategoriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoriesController {
    @Autowired
    CategoriesService categoriesService;

    // Get all list category
    @GetMapping("/free/get-all/{page}/{size}")
    public ResponseEntity<List<CategoriesDataModel>> getAll(@PathVariable("page") Integer soTrang,
                                                            @PathVariable("size") Integer soSanPham){
        return ResponseEntity.ok(categoriesService.findAll(soTrang,soSanPham));
    }
    @GetMapping("/free/get-all-admin/{page}/{size}")
    public ResponseEntity<List<CategoriesDataModel>> getAllAdmin(@PathVariable("page") Integer soTrang,
                                                            @PathVariable("size") Integer soSanPham){
        return ResponseEntity.ok(categoriesService.findAllAdmin(soTrang,soSanPham));
    }


    // Insert
    @PostMapping("/crud/save")
    public ResponseEntity<CategoriesDataModel> save(@RequestBody CategoriesDataModel categoriesDataModel) {
        return ResponseEntity.ok(categoriesService.save(categoriesDataModel));
    }

    //Delete
    @PostMapping("/crud/delete/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(categoriesService.delete(id));
    }
}
