package com.java.service;

import java.util.Collection;
import org.springframework.stereotype.Service;
import com.java.entity.Book;
import com.java.entity.CartItem;

@Service
public interface ShoppingCartService {

    int getCount(String userId);

    double getAmount(String userId);

    void clear(int cartId);

    Collection<CartItem> getCartItems(String userId);

    void remove(CartItem item);

    void add(CartItem item);
    void addItemToCart(String userId, CartItem item);
    void remove(Book book);

    /**
     * Cập nhật số lượng của mặt hàng trong giỏ
     * @param item - mặt hàng cần cập nhật
     * @param qty - số lượng mới
     */
    void update(CartItem item, int qty);

}
