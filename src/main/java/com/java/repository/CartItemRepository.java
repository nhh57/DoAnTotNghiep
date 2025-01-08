package com.java.repository;

import com.java.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;

public interface CartItemRepository extends JpaRepository<CartItem, Integer> {


    CartItem findByBookId(Integer bookId);

    void deleteByCartId(Integer cartId);

    @Modifying
    @Transactional
    @Query("DELETE FROM CartItem c WHERE c.bookId = :bookId AND c.id = :id")
    void deleteByBookId(@Param("bookId") Integer bookId, @Param("id") Integer id);
}