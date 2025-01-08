package com.java.util;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import com.java.entity.Customer;

@Component
public class UserUtils {

    public Customer getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Kiểm tra nếu chưa đăng nhập hoặc không xác thực
        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }

        Object principal = authentication.getPrincipal();

        // Kiểm tra kiểu của principal
        if (principal instanceof Customer) {
            return (Customer) principal;
        } else if (principal instanceof UserDetails) {
            // Nếu principal là UserDetails, có thể lấy thông tin username
            String username = ((UserDetails) principal).getUsername();
            return new Customer(username);  // Tạo customer tạm (hoặc query từ DB)
        } else if (principal instanceof String) {
            // Trường hợp principal là String (username)
            String username = (String) principal;
            return new Customer(username);  // Tạo customer tạm
        }

        // Trả về null nếu không tìm thấy
        return null;
    }

    public String getCurrentUserId() {
        Customer customer = getCurrentUser();
        if (customer == null) {
            throw new SecurityException("Người dùng chưa đăng nhập!");
        }
        return customer.getCustomerId();
    }
}