package com.example.ecommerce.repository;

import com.example.ecommerce.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author hainh
 */
@Repository("AccountRepo")
public interface AccountRepo extends JpaRepository<Account,Integer> {
    /**
     * <p>getAllAccount</p>
     * @param keySearch,isDeleted
     * @return DAO
     */
    @Modifying(clearAutomatically = true)
    @Query(value = "SELECT a.id, a.username, a.password, a.email, a.date_of_birth, a.full_name, a.phone, a.ship_detail_id, a.cart_id, a.is_deleted, a.created_at, a.updated_at FROM Account a WHERE ((?1 <> '' AND (a.username LIKE CONCAT('%',?1,'%') OR a.username LIKE CONCAT('%',?1,'%') OR a.phone LIKE CONCAT('%',?1,'%') OR a.full_name LIKE CONCAT('%',?1,'%') OR a.email LIKE CONCAT('%',?1,'%') )) OR ?1 = '') AND (a.is_deleted >= 0 AND a.is_deleted = ?2) OR (?2 < 0)",nativeQuery = true)
    List<Account> getAllAccount(String keySearch, int isDeleted) throws Exception;

    /**
     * <p>findOneAccount</p>
     * @param keySearch
     * @return DAO
     */
    @Modifying(clearAutomatically = true)
    @Query(value = "SELECT a.id, a.username, a.password, a.email, a.date_of_birth, a.full_name, a.phone, a.ship_detail_id, a.cart_id, a.is_deleted, a.created_at, a.updated_at FROM Account a WHERE ((?1 <> '' AND (a.username LIKE CONCAT('%',?1,'%') OR a.username LIKE CONCAT('%',?1,'%') OR a.phone LIKE CONCAT('%',?1,'%') OR a.full_name LIKE CONCAT('%',?1,'%') OR a.email LIKE CONCAT('%',?1,'%'))) OR ?1 = '')",nativeQuery = true)
    List<Account> getAccount(String keySearch) throws Exception;



}
