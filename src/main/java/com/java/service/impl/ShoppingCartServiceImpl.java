package com.java.service.impl;

import java.util.*;

import com.java.entity.Cart;
import com.java.entity.Customer;
import com.java.repository.CartItemRepository;
import com.java.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import com.java.entity.Book;
import com.java.entity.CartItem;
import com.java.service.ShoppingCartService;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {
    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;
    private Map<Integer, CartItem> map = new HashMap<>();

    // Thêm sản phẩm vào giỏ hàng
    @Override
    public void add(CartItem item) {
        CartItem existedItem = map.get(item.getBookId());

        if (existedItem != null) {
            existedItem.setQuantity(item.getQuantity() + existedItem.getQuantity());
            existedItem.setTotalPrice(existedItem.getQuantity() * existedItem.getUnitPrice());
        } else {
            map.put(item.getBookId(), item);
        }
    }

    @Override
    public void addItemToCart(String userId, CartItem item) {
        try {
            // Tìm cart của người dùng
            Cart cart = cartRepository.findByUserId(userId);

            // Nếu chưa có cart thì tạo mới
            if (cart == null) {
                cart = new Cart();
                cart.setUserId(userId);
                cart.setItems(new ArrayList<>());  // Đảm bảo items không bị null
                cartRepository.save(cart);  // Lưu Cart trước, đảm bảo Cart có id
            }

            // Kiểm tra xem sản phẩm đã tồn tại trong giỏ hàng hay chưa
            CartItem existedItem = cart.getItems()
                    .stream()
                    .filter(i -> i.getBookId().equals(item.getBookId()))
                    .findFirst()
                    .orElse(null);

            if (existedItem != null) {
                // Nếu đã có, cập nhật số lượng
                existedItem.setQuantity(existedItem.getQuantity() + item.getQuantity());
                existedItem.setTotalPrice(existedItem.getQuantity() * existedItem.getUnitPrice());
            } else {
                // Gán cart cho CartItem
                item.setCart(cart);
                cart.getItems().add(item);  // Thêm CartItem vào giỏ hàng

            }

            // Sau khi CartItem đã được lưu, lưu lại Cart nếu có thay đổi (trong trường hợp đã thêm CartItem mới)
            cartRepository.save(cart);  // Lưu lại Cart nếu có thay đổi

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    // Cập nhật số lượng sản phẩm trong giỏ hàng (Cả CartItem và số lượng)
    @Override
    public void update(CartItem item, int qty) {
        CartItem existingItem = cartItemRepository.findById(item.getId()).orElse(null);

        if (existingItem != null) {
            int availableStock = item.getBook().getQuality();
            if (qty > availableStock) {
                throw new IllegalArgumentException("Số lượng yêu cầu vượt quá số lượng tồn kho.");
            }

            existingItem.setQuantity(qty);
            existingItem.setTotalPrice(existingItem.getQuantity() * existingItem.getUnitPrice());
            cartItemRepository.saveAndFlush(existingItem);
        }
    }


//    @Override
//    public void update(CartItem item, int qty) {
//        // Tìm sản phẩm trong giỏ hàng theo ID
//        CartItem existingItem = map.get(item.getBookId());
//
//        if (existingItem != null) {
//            // Cập nhật lại số lượng sản phẩm
//            existingItem.setQuantity(qty);
//            // Tính lại tổng tiền cho sản phẩm
//            existingItem.setTotalPrice(existingItem.getQuantity() * existingItem.getUnitPrice());
//        }
//    }

    // Lấy thông tin sản phẩm trong giỏ hàng theo ID
    public CartItem getItemById(Integer bookId) {
        return map.get(bookId);
    }

    // Xóa sản phẩm khỏi giỏ hàng theo CartItem
    @Override
    public void remove(int bookId, int cartId) {
        cartItemRepository.deleteByBookId(bookId,cartId);
    }

    // Xóa sản phẩm khỏi giỏ hàng theo Book ID
    public void removeById(Integer bookId) {
        map.remove(bookId);
    }

    // Bổ sung phương thức bắt buộc từ interface ShoppingCartService
    @Override
    public void remove(Book book) {
        if (book != null) {
            map.remove(book.getId()); // Giả sử `Book` có phương thức `getId()` để lấy `bookId`
        }
    }

    // Trả về toàn bộ giỏ hàng dưới dạng Map
    public Map<Integer, CartItem> getItems() {
        return map;
    }

    // Lấy danh sách tất cả sản phẩm trong giỏ hàng
    @Override
    public Collection<CartItem> getCartItems(String userId) {

        Cart cart = cartRepository.findByUserId(userId);
        if (cart == null) {
            return Collections.emptyList();
        }
        map.clear();
        cart.getItems().forEach(item -> map.put(item.getBookId(), item));
        return map.values();
    }

    // Xóa toàn bộ giỏ hàng
    @Override
    public void clear(int cartId) {
        cartItemRepository.deleteByCartId(cartId);
        map.clear();
    }

    // Tính tổng tiền giỏ hàng
    @Override
    public double getAmount(String userId) {
        Cart cart = cartRepository.findByUserId(userId);
        if (cart == null) {
            return 0.0;
        }

        return cart.getItems().stream().mapToDouble(item -> item.getQuantity() * item.getUnitPrice()).sum();

//        return map.values().stream()
//                .mapToDouble(item -> item.getQuantity() * item.getUnitPrice())
//                .sum();
    }

    // Đếm số lượng sản phẩm trong giỏ hàng
    @Override
    public int getCount(String userId) {
        Cart cart = cartRepository.findByUserId(userId);
        if (cart == null || cart.getItems().isEmpty()) {
            return 0;
        }

        return cart.getItems().size();

    }

    // Kiểm tra giỏ hàng có rỗng hay không
    public boolean isEmpty() {
        return map.isEmpty();
    }

    // Kiểm tra xem sản phẩm có tồn tại trong giỏ hàng hay không
    public boolean containsItem(Integer bookId) {
        return map.containsKey(bookId);
    }
}
