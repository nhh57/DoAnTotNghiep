package com.example.ecommerce.service;

import com.example.ecommerce.model.Categories;

import java.util.List;

public interface CategoriesService {
    Categories findOne(int id) throws Exception;
}