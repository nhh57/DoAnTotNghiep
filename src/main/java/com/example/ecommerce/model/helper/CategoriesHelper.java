package com.example.ecommerce.model.helper;

import com.example.ecommerce.model.Categories;
import com.example.ecommerce.model.data.CategoriesDataModel;

public class CategoriesHelper {
    public CategoriesDataModel getCategoriesDataModel(Categories categories){
        CategoriesDataModel categoriesDataModel=new CategoriesDataModel();
        if(categories!=null){
            categoriesDataModel.setId(categories.getId());
            categoriesDataModel.setCategoryName(categories.getCategoryName());
            categoriesDataModel.setIsDeleted(categories.getDeleted());
        }
        return categoriesDataModel;
    }
}
