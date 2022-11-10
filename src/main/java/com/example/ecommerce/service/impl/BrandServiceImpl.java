package com.example.ecommerce.service.impl;

import com.example.ecommerce.model.Brand;
import com.example.ecommerce.model.data.BrandDataModel;
import com.example.ecommerce.model.helper.BrandHelper;
import com.example.ecommerce.repository.BrandRepo;
import com.example.ecommerce.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class BrandServiceImpl implements BrandService {
    @Autowired
    BrandRepo brandRepo;

    BrandHelper brandHelper=new BrandHelper();
    @Override
    public List<BrandDataModel> findAll(Integer soTrang,Integer soSanPham) {
        Pageable pageable = PageRequest.of(soTrang-1, soSanPham);
        Page<Brand> pageBrand=brandRepo.findBrandExist(pageable);
        List<Brand> list=pageBrand.getContent();
        List<BrandDataModel> listResult=brandHelper.getListCategoriesDataModel(list);
        return listResult;
    }

    @Override
    public List<BrandDataModel> findAllAdmin(Integer soTrang,Integer soSanPham) {
        Pageable pageable = PageRequest.of(soTrang-1, soSanPham);
        Page<Brand> pageBrand=brandRepo.findAll(pageable);
        List<Brand> list=pageBrand.getContent();
        List<BrandDataModel> listResult=brandHelper.getListCategoriesDataModel(list);
        return listResult;
    }

    @Override
    public Boolean existsById(Integer id) {
        return brandRepo.existsById(id);
    }

    @Override
    public BrandDataModel save(BrandDataModel brandDataModel) {
        Brand brandSaved=brandRepo.save(brandHelper.getBrand(brandDataModel));
        return brandHelper.getBrandDataModel(brandSaved);
    }

    @Override
    public Boolean delete(Integer id) {
        Brand brand=brandRepo.findById(id).get();
        if(brand!=null){
            brand.setDeleted(true);
            brandHelper.getBrandDataModel(brandRepo.save(brand));
            return true;
        }else{
            return false;
        }
    }
}
