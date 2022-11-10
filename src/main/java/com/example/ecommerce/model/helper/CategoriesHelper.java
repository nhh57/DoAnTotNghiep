package com.example.ecommerce.model.helper;

import com.example.ecommerce.model.Categories;
import com.example.ecommerce.model.data.CategoriesDataModel;

import java.util.ArrayList;
import java.util.List;

public class CategoriesHelper {
    public CategoriesDataModel getCategoriesDataModel(Categories categories){
        CategoriesDataModel categoriesDataModel=new CategoriesDataModel();
        categoriesDataModel.setId(categories.getId());
        categoriesDataModel.setCategoryName(categories.getCategoryName());
        categoriesDataModel.setIsDeleted(categories.getDeleted());
        return categoriesDataModel;
    }
    public List<CategoriesDataModel> getListCategoriesDataModel(List<Categories> listCategories){
        List<CategoriesDataModel> list=new ArrayList<>();
        for(Categories categories:listCategories){
            list.add(getCategoriesDataModel(categories));
        }
        return list;
    }

    public Categories getCategories(CategoriesDataModel categoriesDataModel){
        Categories categories=new Categories();
        categories.setId(categoriesDataModel.getId());
        categories.setCategoryName(categoriesDataModel.getCategoryName());
        categories.setDeleted(categoriesDataModel.getIsDeleted());
        return categories;
    }
}
