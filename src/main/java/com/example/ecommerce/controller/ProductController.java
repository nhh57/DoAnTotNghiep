package com.example.ecommerce.controller;

import com.example.ecommerce.common.Utils;
import com.example.ecommerce.model.Categories;
import com.example.ecommerce.model.Product;
import com.example.ecommerce.model.data.ProductDataModel;
import com.example.ecommerce.model.data.ProductDataModelCreate;
import com.example.ecommerce.request.ProductRequest;
import com.example.ecommerce.response.BaseResponse;
import com.example.ecommerce.service.BrandService;
import com.example.ecommerce.service.CategoriesService;
import com.example.ecommerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ProductController {
    @Autowired
    private ProductService productService;

    @Autowired
    private CategoriesService categoriesService;

    @Autowired
    private BrandService brandService;

    // Get all list product exist
    @GetMapping("/product/getall")
    public ResponseEntity<List<ProductDataModel>> getAll(){
        return ResponseEntity.ok(productService.findProductExist());
    }
    // Get all list product
    @GetMapping("/product/getalladmin")
    public ResponseEntity<List<ProductDataModel>> getAllAdmin(){
        return ResponseEntity.ok(productService.findAll());
    }
    // Find by name
    @GetMapping("/product/find/{name}")
    public ResponseEntity<List<ProductDataModel>> get(@PathVariable("name") String name) {
        return ResponseEntity.ok(productService.findByName(name));
    }
    // Find by price
    @GetMapping("/product/find/{min}/{max}")
    public ResponseEntity<List<ProductDataModel>> get(@PathVariable("min") BigDecimal minPrice, @PathVariable("max") BigDecimal maxPrice) {
        return ResponseEntity.ok(productService.findByPrice(minPrice, maxPrice));
    }

    // Find by best selling products
    @GetMapping("/product/find/numberofproduct")
    public ResponseEntity<List<Product>> get(@PathVariable("numberofproduct") Integer numberOfProduct) {
        return ResponseEntity.ok(productService.findByBestSellingProducts(numberOfProduct));
    }

    //Get one product
    @GetMapping("/product/getone/{id}")
    public ResponseEntity<ProductDataModel> getOne(@PathVariable("id") Integer id) {
        if (!productService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(productService.findById(id));
    }

    // Insert
    @PostMapping("/product/insert")
    public ResponseEntity<ProductDataModel> post(@RequestBody ProductDataModel productDataModel) {
//        if (productService.existsById(productDataModel.getId())) {
//            return ResponseEntity.badRequest().build();
//        }
//        productService.save(productDataModel);
        return ResponseEntity.ok(productDataModel);
    }


    @RequestMapping(value = "/product/create-product", method = RequestMethod.POST, produces = {
            MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<BaseResponse> createProduct(@Valid @RequestBody ProductRequest request) throws Exception {
        BaseResponse response = new BaseResponse();
            Product product =  productService.findByProductName(request.getProductName());
            if(product != null){
                response.setStatus(HttpStatus.BAD_REQUEST);
                response.setMessageError("Tên sản phẩm đã tồn tại");
                return new ResponseEntity<BaseResponse>(response, HttpStatus.BAD_REQUEST);
            }
        ProductDataModelCreate productDataModel = new ProductDataModelCreate();
            productDataModel.setProductName(request.getProductName());
            productDataModel.setPrice(request.getPrice());
            productDataModel.setDiscount(request.getDiscount());
            productDataModel.setNote(request.getNote());
            productDataModel.setImages(request.getImages());
            productDataModel.setNumberOfSale(request.getNumberOfSale());
            productDataModel.setCategory(Utils.convertObjectToJsonString(request.getCategory()));
            productDataModel.setBrand(Utils.convertObjectToJsonString(request.getBrand()));
        productService.createProductDataModel(productDataModel);
        return new ResponseEntity<BaseResponse>(response, HttpStatus.OK);
    }

    // Update
    @PostMapping("/product/update/{id}")
    public ResponseEntity<ProductDataModel> put(@PathVariable("id") Integer id, @RequestBody ProductDataModel productDataModel) {
        if (!productService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        productService.save(productDataModel);
        return ResponseEntity.ok(productDataModel);
    }

    //Delete
    @PostMapping("/product/delete/{id}")
    public ResponseEntity<ProductDataModel> delete(@PathVariable("id") Integer id) {
        ProductDataModel productDataModel= productService.findById(id);
//        if (!productService.existsById(id)) {
//            return ResponseEntity.notFound().build();
//        }
//        productDataModel.setIsDeleted(true);
//        productService.save(productDataModel);
        return ResponseEntity.ok(productDataModel);
    }
}
