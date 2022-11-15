package com.example.ecommerce.mvc.controller;

import com.example.ecommerce.model.Account;
import com.example.ecommerce.model.CartDetail;
import com.example.ecommerce.model.Product;
import com.example.ecommerce.model.ShipDetail;
import com.example.ecommerce.model.helper.CartHelper;
import com.example.ecommerce.mvc.dao.SessionDAO;
import com.example.ecommerce.mvc.dao.ShoppingCartDAO;
import com.example.ecommerce.mvc.model.ShoppingCart;
import com.example.ecommerce.repository.CartDetailRepo;
import com.example.ecommerce.repository.ProductRepo;
import com.example.ecommerce.repository.ShipDetailRepo;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;


@Controller
@RequestMapping("mvc/shopping-cart")
public class ShoppingCartController {
	@Autowired
	ProductRepo productDAO;
	@Autowired
	ShoppingCartDAO shoppingCartDAO;

	@Autowired
	CartDetailRepo cartDetailRepo;

	@Autowired
	ShipDetailRepo shipDetailRepo;

	@Autowired
	SessionDAO session;

	CartHelper cartHelper=new CartHelper();
	
	@GetMapping("")
	public String viewCart(Model model,
						   @RequestParam("orderSaved") Optional<String> orderSaved,
						   @RequestParam("addressNull") Optional<String> addressNull,
						   @RequestParam("orderId") Optional<String> orderId,
						   @ModelAttribute("user") Account user) {
		if(addressNull.isPresent()){
			model.addAttribute("addressNull","Chọn hoặc điền 1 địa chỉ!");
		}
		if(orderSaved.isPresent()){
			if(orderSaved.get().equals("true") && orderId.isPresent()){
				model.addAttribute("orderSaved",true);
				model.addAttribute("orderIdSaved",orderId.get());
			}else {
				model.addAttribute("error","Đặt hàng thất bại");
			}
		}
		Account khachHang=session.get("user") != null ? (Account) session.get("user") : null;
		Integer cartId= khachHang != null ? khachHang.getCartId() : null;
		if(khachHang!=null) {
			model.addAttribute("sessionUsername",khachHang.getUsername());
			model.addAttribute("user",khachHang);
			shoppingCartDAO.getAll().forEach(item -> {
				CartDetail cartDetail=cartDetailRepo.existByProductId(cartId,item.getId());
				if(cartDetail != null){
					cartDetail.setAmount(cartDetail.getAmount()+item.getSoLuong());
					cartDetailRepo.saveAndFlush(cartDetail);
				}else{
					CartDetail cartDetailNew=new CartDetail();
					cartDetailNew.setCartId(cartId);
					cartDetailNew.setProductId(item.getId());
					cartDetailNew.setAmount(item.getSoLuong());
					cartDetailRepo.saveAndFlush(cartDetailNew);
				}
			});
			shoppingCartDAO.clear();
		}
		if(khachHang != null && cartId != null){
			List<CartDetail> listCart=cartDetailRepo.getCartDetail(cartId);
			if(listCart.size() > 0 ){
				if(cartHelper.checkNullProductByProductId(listCart)){
					for(CartDetail item:listCart){
						item.setProductByProductId(productDAO.findById(item.getProductId()).get());
					}
				}
			}
			model.addAttribute("cartId",cartId);
			model.addAttribute("checkCart",true);
			model.addAttribute("listGioHangLogin",listCart);
			model.addAttribute("listDiaChi",shipDetailRepo.findByAccountId(khachHang.getId()));
			model.addAttribute("tongTienGioHang",cartHelper.getTotalMoneyCart(listCart));
			model.addAttribute("tongSoLuongGioHang",cartHelper.getNumberOfListCart(listCart));
		}else{
			Collection<ShoppingCart> listGioHang = shoppingCartDAO.getAll();
			model.addAttribute("checkCart",false);
			model.addAttribute("listGioHang",listGioHang);
			model.addAttribute("tongTienGioHang",shoppingCartDAO.getAmout());
			model.addAttribute("tongSoLuongGioHang",shoppingCartDAO.getCount());
		}
		return "customer/cart";
	}
	@PostMapping("addToCart")
	public ResponseEntity<String> addToCart(@RequestParam("maSanPham") String maSanPhamString,
											@RequestParam("soLuong") String soLuongString) throws JSONException {
		Account khachHang=session.get("user") != null ? (Account) session.get("user") : null;
		Integer cartId= khachHang != null ? khachHang.getCartId() : null;
		Integer maSanPham=Integer.parseInt(maSanPhamString);
		Integer soLuong=Integer.parseInt(soLuongString);
		if(khachHang != null && cartId != null){
			CartDetail cartDetail=cartDetailRepo.existByProductId(cartId,maSanPham);
			if(cartDetail != null){
				cartDetail.setAmount(cartDetail.getAmount()+soLuong);
				cartDetailRepo.save(cartDetail);
			}else{
				CartDetail cartDetailNew=new CartDetail();
				cartDetailNew.setCartId(cartId);
				cartDetailNew.setProductId(maSanPham);
				cartDetailNew.setAmount(soLuong);
				cartDetailRepo.save(cartDetailNew);
			}
			List<CartDetail> listCart=cartDetailRepo.getCartDetail(cartId);
			int tongSoLuongGioHang = cartHelper.getNumberOfListCart(listCart);
			JSONObject json = new JSONObject();
			json.put("soLuong", tongSoLuongGioHang);
			return ResponseEntity.ok(String.valueOf(json));
		}else{
			Product product=productDAO.findById(maSanPham).get();
			ShoppingCart cartItem=new ShoppingCart();
			cartItem.setId(product.getId());
			cartItem.setPrice(product.getPrice());
			cartItem.setDiscount(product.getDiscount());
			cartItem.setImages(product.getImages());
			cartItem.setBrandByBrandId(product.getBrandByBrandId());
			cartItem.setCategoriesByCategoryId(product.getCategoriesByCategoryId());
			cartItem.setNote(product.getNote());
			cartItem.setProductName(product.getProductName());
			cartItem.setNumberOfSale(product.getNumberOfSale());
			cartItem.setSoLuong(soLuong);
			shoppingCartDAO.add(cartItem,soLuong);
			int tongSoLuongGioHang = shoppingCartDAO.getCount();
			JSONObject json = new JSONObject();
			json.put("soLuong", tongSoLuongGioHang);
			return ResponseEntity.ok(String.valueOf(json));
		}
	}
	@PostMapping("changeCount")
	public ResponseEntity<String> editCart(@RequestParam("maSanPham") String maSanPhamString,
										   @RequestParam("soLuongSanPham") String soLuongSanPhamString) throws JSONException {
		Integer maSanPham=Integer.parseInt(maSanPhamString);
		Integer soLuongSanPham=Integer.parseInt(soLuongSanPhamString);
		Account khachHang=session.get("user") != null ? (Account) session.get("user") : null;
		Integer cartId= khachHang != null ? khachHang.getCartId() : null;
		if(khachHang != null && cartId != null){
			CartDetail item=cartDetailRepo.existByProductId(cartId,maSanPham);
			item.setAmount(soLuongSanPham);
			CartDetail cartDetailSaved=cartDetailRepo.save(item);
			List<CartDetail> listCart=cartDetailRepo.getCartDetail(cartId);
			int tongTien = cartHelper.getTotalMoneyCart(listCart);
			int tongSoLuongGioHang = cartHelper.getNumberOfListCart(listCart);
			int tongTienItem=cartHelper.getTotalMoneyOfOneProduct(cartDetailSaved);
			JSONObject json = new JSONObject();
			json.put("soLuong", tongSoLuongGioHang);
			json.put("tongTien", tongTien);
			json.put("tongTienItem",tongTienItem);
			return ResponseEntity.ok(String.valueOf(json));
		}else{
			ShoppingCart cartItem=shoppingCartDAO.update(maSanPham,soLuongSanPham);
			int tongTien = shoppingCartDAO.getAmout();
			int tongSoLuongGioHang = shoppingCartDAO.getCount();
			int tongTienItem=shoppingCartDAO.getTotalMoneyOfOneProduct(maSanPham);
			JSONObject json = new JSONObject();
			json.put("soLuong", tongSoLuongGioHang);
			json.put("tongTien", tongTien);
			json.put("tongTienItem",tongTienItem);
			return ResponseEntity.ok(String.valueOf(json));
		}
	}
	@PostMapping("deleteItem")
	public ResponseEntity<String> deleteItem(@RequestParam("maSanPham") String maSanPhamString) throws JSONException {
		Integer maSanPham=Integer.parseInt(maSanPhamString);
		Account khachHang=session.get("user") != null ? (Account) session.get("user") : null;
		Integer cartId= khachHang != null ? khachHang.getCartId() : null;
		if(khachHang != null && cartId != null){
			cartDetailRepo.deleteByProductId(cartId,maSanPham);
			List<CartDetail> listCart=cartDetailRepo.getCartDetail(cartId);
			int tongTien = cartHelper.getTotalMoneyCart(listCart);
			int tongSoLuongGioHang = cartHelper.getNumberOfListCart(listCart);
			JSONObject json = new JSONObject();
			json.put("soLuong", tongSoLuongGioHang);
			json.put("tongTien", tongTien);
			return ResponseEntity.ok(String.valueOf(json));
		}else{
			shoppingCartDAO.remove(maSanPham);
			int tongTien = shoppingCartDAO.getAmout();
			int tongSoLuongGioHang = shoppingCartDAO.getCount();
			JSONObject json = new JSONObject();
			json.put("soLuong", tongSoLuongGioHang);
			json.put("tongTien", tongTien);
			return ResponseEntity.ok(String.valueOf(json));
		}
	}
	@GetMapping("deleteAllItem")
	public String deleteAllItem(Model model) {
		Account khachHang=session.get("user") != null ? (Account) session.get("user") : null;
		Integer cartId= khachHang != null ? khachHang.getCartId() : null;
		if(khachHang != null && cartId != null){
			cartDetailRepo.deleteAllByCartId(cartId);
		}else{
			shoppingCartDAO.clear();
		}
		return "redirect:/mvc/shopping-cart";
	}
}
