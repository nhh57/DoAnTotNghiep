/*
package com.example.ecommerce.repository;

import com.example.ecommerce.model.Account;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
@Repository
public interface AdminRepo extends JpaRepository<Account, Integer> {
    @Modifying(clearAutomatically = true)
    @Query(value =  "SELECT a.id, a.username, a.password, a.email, a.date_of_birth, a.full_name, a.phone, a.is_deleted, a.created_at, a.updated_at " +
                    "FROM   account a " +
                    "WHERE  ((?1 <> '' AND (a.username LIKE CONCAT('%',?1,'%') OR a.username LIKE CONCAT('%',?1,'%') OR a.phone LIKE CONCAT('%',?1,'%') OR a.full_name LIKE CONCAT('%',?1,'%') OR a.email LIKE CONCAT('%',?1,'%') )) OR ?1 = '') " +
                            "AND ((?3 IS NOT NULL AND DATE_FORMAT(a.created_at , '%Y-%m-%d') >= ?3) OR ?3 IS NULL)" +
                            "AND ((?4 IS NOT NULL AND DATE_FORMAT(a.created_at , '%Y-%m-%d') <= ?4) OR ?4 IS NULL)   ", nativeQuery = true)
    List<Account> getAllAccount(String keySearch, Date fromDate, Date toDate, Pageable pageable) throws Exception;

}
*/
