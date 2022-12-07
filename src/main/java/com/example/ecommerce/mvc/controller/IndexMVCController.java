package com.example.ecommerce.mvc.controller;

import com.example.ecommerce.common.Utils;
import com.example.ecommerce.model.*;
import com.example.ecommerce.model.helper.CartHelper;
import com.example.ecommerce.model.helper.SaleHelper;
import com.example.ecommerce.mvc.dao.SessionDAO;
import com.example.ecommerce.mvc.dao.ShoppingCartDAO;
import com.example.ecommerce.mvc.model.SaleResult;
import com.example.ecommerce.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("mvc")
public class IndexMVCController {
    @Autowired
    ProductRepo productDAO;

    @Autowired
    ShoppingCartDAO shoppingCartDAO;
    @Autowired
    CartDetailRepo cartDetailRepo;

    @Autowired
    SaleRepo saleRepo;

    @Autowired
    SaleDetailRepo saleDetailRepo;

    CartHelper cartHelper = new CartHelper();

    @Autowired
    SessionDAO session;

    SaleHelper saleHelper = new SaleHelper();

    @GetMapping("/index")
    public String index(Model model, @RequestParam Optional<String> message) throws ParseException {
        if (message.isPresent()) {
            model.addAttribute("message", message.get());
        }
        Pageable pageable = PageRequest.of(0, 4);
        Page<Product> pageProduct = productDAO.findByBrandId(pageable,1);
        List<Product> list1 = pageProduct.getContent();
        List<Product> list2 = productDAO.findByBrandId(pageable,2).getContent();
        List<Product> list3 = productDAO.findByBrandId(pageable,3).getContent();
        model.addAttribute("listProductByBrandId1", list1);
        model.addAttribute("listProductByBrandId2", list2);
        model.addAttribute("listProductByBrandId3", list3);
        model.addAttribute("listBestSelling", productDAO.findByBestSellingProducts(10));
        //Flash sale
        Sale sale=saleRepo.findByRecentDay();
        SaleResult saleResult=saleHelper.getSaleResult(sale);
        List<SaleDetail> listSaleDetail=saleDetailRepo.findAllBySaleId(saleResult.getId());
        Date now=new Date();
        Date dateStart= Utils.getDateTimeFromDateAndTimeString(saleResult.getSaleDateStart(),saleResult.getSaleTimeStart());
        Date dateEnd= Utils.getDateTimeFromDateAndTimeString(saleResult.getSaleDateEnd(),saleResult.getSaleTimeEnd());
        double timeLeft=Utils.date1MinusDate2ToHours(dateStart,now);
        double timeEnd=Utils.date1MinusDate2ToHours(dateEnd,now);
        //Nếu thời gian KM cách thời gian hiện tại dưới 3 tiếng thì bắt đầu đếm nhưng không hiện sp
        if(timeLeft <= 3){
            saleResult.setShowSale(true);
        }else{
            saleResult.setShowSale(false);
        }
        //Nếu thời gian bắt đầu KM bé hơn hoặc bằng now thì sự kiện bắt đầu
        if(timeLeft <=0 && timeEnd >= 0){
            for(SaleDetail item:listSaleDetail){
                Product product=item.getProduct();
                product.setDiscount(item.getDiscountSale());
                productDAO.save(product);
            }
            saleResult.setShowProduct(true);
            saleResult.setShowSale(false);
        }
        //Nếu thời gian kết thúc KM bé hơn now thì sự kiện kết thúc
        if(timeEnd < 0){
            for(SaleDetail item:listSaleDetail){
                Product product=item.getProduct();
                product.setDiscount(item.getDiscountOld());
                productDAO.save(product);
            }
            saleResult.setShowSale(false);
            saleResult.setShowSale(false);
            sale.setIsDeleted(true);
            saleRepo.save(sale);
        }

        model.addAttribute("sale",saleResult);
        model.addAttribute("listSaleDetail",listSaleDetail);

        //Set số lượng giỏ hàng
        Account khachHang = (Account) session.get("user");
        Integer cartId = khachHang != null ? khachHang.getCartId() : null;
        if (khachHang != null) {
            shoppingCartDAO.getAll().forEach(item -> {
                CartDetail cartDetail = cartDetailRepo.existByProductId(cartId, item.getId());
                if (cartDetail != null) {
                    cartDetail.setAmount(cartDetail.getAmount() + item.getSoLuong());
                    cartDetailRepo.saveAndFlush(cartDetail);
                } else {
                    CartDetail cartDetailNew = new CartDetail();
                    cartDetailNew.setCartId(cartId);
                    cartDetailNew.setProductId(item.getId());
                    cartDetailNew.setAmount(item.getSoLuong());
                    cartDetailRepo.saveAndFlush(cartDetailNew);
                }
            });
            shoppingCartDAO.clear();

            List<CartDetail> listCart = cartDetailRepo.getCartDetail(khachHang.getCartId());
            model.addAttribute("tongSoLuongGioHang", cartHelper.getNumberOfListCart(listCart));
            model.addAttribute("sessionUsername", khachHang.getUsername());
        } else {
            model.addAttribute("tongSoLuongGioHang", shoppingCartDAO.getCount());
        }
        return "customer/index";
    }
}
