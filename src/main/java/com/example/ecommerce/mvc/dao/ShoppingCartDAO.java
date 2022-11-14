package com.example.ecommerce.mvc.dao;

import com.example.ecommerce.mvc.model.ShoppingCart;

import java.util.Collection;

public interface ShoppingCartDAO {
	public void add(ShoppingCart item, Integer soLuong);
	public void remove(int id);
	public int getAmout();
	public int getCount();
	public int getTotalMoneyOfOneProduct(int productId);
	public Collection<ShoppingCart> getAll();
	public void clear();
	public ShoppingCart update(int productId, int soLuong);
}
