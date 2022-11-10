package com.example.ecommerce.controller;
import com.example.ecommerce.model.Product;
import com.example.ecommerce.model.data.ProductDataModel;
import com.example.ecommerce.model.helper.ProductHelper;
import com.example.ecommerce.model.result.ProductResult;
import com.example.ecommerce.repository.ProductRepo;
import com.example.ecommerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/product")
public class ProductController {
    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepo productRepo;

    ProductHelper productHelper=new ProductHelper();


    // Get all list product exist
    @GetMapping("/free/get-all/{page}/{size}")
    public ResponseEntity<ProductResult> getAll(@PathVariable("page") Integer soTrang,
                                                         @PathVariable("size") Integer soSanPham){
        ProductResult productResult=new ProductResult();
        List<ProductDataModel> list=productService.findProductExist(soTrang,soSanPham);
        productResult.setData(list);
        productResult.setTotalPage(productHelper.getTotalPage(soSanPham,productRepo.findProductExist().size()));
        return ResponseEntity.ok(productResult);
    }
    // Get all list product
    @GetMapping("/free/get-all-admin/{page}/{size}")
    public ResponseEntity<ProductResult> getAllAdmin(@PathVariable("page") Integer soTrang,
                                                     @PathVariable("size") Integer soSanPham){
        ProductResult productResult=new ProductResult();
        List<ProductDataModel> list=productService.findAll(soTrang,soSanPham);
        productResult.setData(list);
        productResult.setTotalPage(productHelper.getTotalPage(soSanPham,productRepo.findAll().size()));
        return ResponseEntity.ok(productResult);
    }
    // Find by name
    @GetMapping("/free/find/{name}")
    public ResponseEntity<List<ProductDataModel>> findByName(@PathVariable("name") String name) {
        return ResponseEntity.ok(productService.findByName(name));
    }
    // Find by price
    @GetMapping("/free/find/{min}/{max}")
    public ResponseEntity<List<ProductDataModel>> findByPrice(@PathVariable("min") BigDecimal minPrice, @PathVariable("max") BigDecimal maxPrice) {
        return ResponseEntity.ok(productService.findByPrice(minPrice, maxPrice));
    }

    // Find by best selling products
    @GetMapping("/free/find/number-of-product")
    public ResponseEntity<List<Product>> findBestSelling(@PathVariable("number-of-product") Integer numberOfProduct) {
        return ResponseEntity.ok(productService.findByBestSellingProducts(numberOfProduct));
    }

    //Get one product
    @GetMapping("/free/get-one/{id}")
    public ResponseEntity<ProductDataModel> getOne(@PathVariable("id") Integer id) {
        if (!productService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(productService.findById(id));
    }

    // Insert
    @PostMapping("/crud/save")
    public ResponseEntity<ProductDataModel> save(@RequestBody ProductDataModel productDataModel) {
        return ResponseEntity.ok(productService.save(productDataModel));
    }


//    @RequestMapping(value = "/product/create-product", method = RequestMethod.POST, produces = {
//            MediaType.APPLICATION_JSON_VALUE})
//    public ResponseEntity<BaseResponse> createProduct(@Valid @RequestBody ProductRequest request) throws Exception {
//        BaseResponse response = new BaseResponse();
//            Product product =  productService.findByProductName(request.getProductName());
//            if(product != null){
//                response.setStatus(HttpStatus.BAD_REQUEST);
//                response.setMessageError("Tên sản phẩm đã tồn tại");
//                return new ResponseEntity<BaseResponse>(response, HttpStatus.BAD_REQUEST);
//            }
//        ProductDataModelCreate productDataModel = new ProductDataModelCreate();
//            productDataModel.setProductName(request.getProductName());
//            productDataModel.setPrice(request.getPrice());
//            productDataModel.setDiscount(request.getDiscount());
//            productDataModel.setNote(request.getNote());
//            productDataModel.setImages(request.getImages());
//            productDataModel.setNumberOfSale(request.getNumberOfSale());
//            productDataModel.setCategory(Utils.convertObjectToJsonString(request.getCategory()));
//            productDataModel.setBrand(Utils.convertObjectToJsonString(request.getBrand()));
//        productService.createProductDataModel(productDataModel);
//        return new ResponseEntity<BaseResponse>(response, HttpStatus.OK);
//    }

    //Delete
    @PostMapping("/crud/delete/{id}")
    public ResponseEntity<ProductDataModel> delete(@PathVariable("id") Integer id) {
        ProductDataModel productDataModel= productService.findById(id);
        if (!productService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        productDataModel.setIsDeleted(true);
        productService.save(productDataModel);
        return ResponseEntity.ok(productDataModel);
    }
}
