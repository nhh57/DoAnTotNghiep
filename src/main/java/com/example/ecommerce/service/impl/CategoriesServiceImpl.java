package com.example.ecommerce.service.impl;

import com.example.ecommerce.model.Categories;
import com.example.ecommerce.model.Product;
import com.example.ecommerce.model.data.CategoriesDataModel;
import com.example.ecommerce.model.data.ProductDataModel;
import com.example.ecommerce.model.helper.CategoriesHelper;
import com.example.ecommerce.repository.CategoriesRepo;
import com.example.ecommerce.service.CategoriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class CategoriesServiceImpl implements CategoriesService {
    @Autowired
    private CategoriesRepo categoriesRepo;

    CategoriesHelper categoriesHelper=new CategoriesHelper();
    @Override
    public List<CategoriesDataModel> findAll(Integer soTrang,Integer soSanPham) {
        Pageable pageable = PageRequest.of(soTrang-1, soSanPham);
        Page<Categories> pageCategories=categoriesRepo.findCategoriesExist(pageable);
        List<Categories> list=pageCategories.getContent();
        List<CategoriesDataModel> listResult=categoriesHelper.getListCategoriesDataModel(list);
        return listResult;
    }

    @Override
    public List<CategoriesDataModel> findAllAdmin(Integer soTrang,Integer soSanPham) {
        Pageable pageable = PageRequest.of(soTrang-1, soSanPham);
        Page<Categories> pageCategories=categoriesRepo.findAll(pageable);
        List<Categories> list=pageCategories.getContent();
        List<CategoriesDataModel> listResult=categoriesHelper.getListCategoriesDataModel(list);
        return listResult;
    }

    @Override
    public Boolean existsById(Integer id) {
        return categoriesRepo.existsById(id);
    }

    @Override
    public CategoriesDataModel save(CategoriesDataModel categoriesDataModel) {
        Categories categoriesSaved=categoriesRepo.save(categoriesHelper.getCategories(categoriesDataModel));
        return categoriesHelper.getCategoriesDataModel(categoriesSaved);
    }

    @Override
    public Boolean delete(Integer id) {
        Categories categories=categoriesRepo.findById(id).get();
        if(categories!=null){
            categories.setDeleted(true);
            categoriesHelper.getCategoriesDataModel(categoriesRepo.save(categories));
            return true;
        }else{
            return false;
        }
    }
}
