package com.example.ecommerce.repository;

import com.example.ecommerce.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("AccountRepo")
public interface AccountRepo extends JpaRepository<Account,Integer> {

    @Modifying(clearAutomatically = true)
    @Query(value =  "SELECT a.id, a.username, a.password, a.email, a.date_of_birth, a.full_name, a.phone, a.ship_detail_id, a.cart_id, a.is_deleted, a.created_at, a.updated_at FROM Account a WHERE (( ?1 <> '' AND a.username = ?1 ) OR a.username) AND a.is_deleted= ?2",nativeQuery = true)
    List<Account> getAllAccount(String username,
                                int isDeleted);
}
