package com.example.ecommerce.model.helper;

import com.example.ecommerce.model.Categories;
import com.example.ecommerce.model.data.CategoriesDataModel;

public class CategoriesHelper {
    public CategoriesDataModel getCategoriesDataModel(Categories categories){
        CategoriesDataModel categoriesDataModel=new CategoriesDataModel();
        categoriesDataModel.setId(categories.getId());
        categoriesDataModel.setCategoryName(categories.getCategoryName());
        categoriesDataModel.setIsDeleted(categories.getDeleted());
        return categoriesDataModel;
    }
    public Categories getCategories(CategoriesDataModel categoriesDataModel){
        Categories categories=new Categories();
        categories.setId(categoriesDataModel.getId());
        categories.setCategoryName(categoriesDataModel.getCategoryName());
        categories.setDeleted(categories.getDeleted());
        return categories;
    }
}
