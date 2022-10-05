package com.example.ecommerce.controller;

import com.example.ecommerce.model.Categories;
import com.example.ecommerce.repository.CategoriesDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CategoriesController {
    @Autowired
    CategoriesDAO categoriesDAO;

    // Get all list category
    @GetMapping("/categories/getall")
    public ResponseEntity<List<Categories>> getAll(){
        return ResponseEntity.ok(categoriesDAO.findAll());
    }

    //Get one category
    @GetMapping("/categories/getone/{id}")
    public ResponseEntity<Categories> getOne(@PathVariable("id") Integer id) {
        if (!categoriesDAO.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(categoriesDAO.findById(id).get());
    }

    // Insert
    @PostMapping("/categories/insert")
    public ResponseEntity<Categories> post(@RequestBody Categories categories) {
        if (categoriesDAO.existsById(categories.getId())) {
            return ResponseEntity.badRequest().build();
        }
        categoriesDAO.save(categories);
        return ResponseEntity.ok(categories);
    }

    // Update
    @PutMapping("/categories/update/{id}")
    public ResponseEntity<Categories> put(@PathVariable("id") Integer id, @RequestBody Categories categories) {
        if (!categoriesDAO.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        categoriesDAO.save(categories);
        return ResponseEntity.ok(categories);
    }

    //Delete
    @DeleteMapping("/categories/update/{id}")
    public ResponseEntity<Categories> delete(@PathVariable("id") Integer id) {
        Categories categories= new Categories();
        if (!categoriesDAO.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        categories.setDeleted(true);
        categoriesDAO.save(categories);
        return ResponseEntity.ok(categories);
    }
}
