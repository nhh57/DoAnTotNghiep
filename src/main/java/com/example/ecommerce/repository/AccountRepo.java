package com.example.ecommerce.repository;

import com.example.ecommerce.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author hainh
 */
@Repository("AccountRepo")
public interface AccountRepo extends JpaRepository<Account, Integer> {
    /**
     * <p>getAllAccount</p>
     *
     * @param keySearch,isDeleted
     * @return DAO
     */
    @Modifying(clearAutomatically = true)
    @Query(value = "SELECT a.id, a.username, a.password, a.email, a.date_of_birth, a.full_name, a.phone, a.ship_detail_id, a.cart_id, a.is_deleted, a.created_at, a.updated_at FROM account a WHERE ((?1 <> '' AND (a.username LIKE CONCAT('%',?1,'%') OR a.username LIKE CONCAT('%',?1,'%') OR a.phone LIKE CONCAT('%',?1,'%') OR a.full_name LIKE CONCAT('%',?1,'%') OR a.email LIKE CONCAT('%',?1,'%') )) OR ?1 = '') AND (a.is_deleted >= 0 AND a.is_deleted = ?2) OR (?2 < 0)", nativeQuery = true)
    List<Account> getAllAccount(String keySearch, int isDeleted) throws Exception;

    /**
     * <p>findOneAccount</p>
     *
     * @param keySearch
     * @return DAO
     */
    @Modifying(clearAutomatically = true)
    @Query(value = "SELECT a.id, a.username, a.password, a.email, a.date_of_birth, a.full_name, a.phone, a.ship_detail_id, a.cart_id, a.is_deleted, a.created_at, a.updated_at FROM account a WHERE ((?1 <> '' AND (a.username LIKE CONCAT('%',?1,'%') OR a.username LIKE CONCAT('%',?1,'%') OR a.phone LIKE CONCAT('%',?1,'%') OR a.full_name LIKE CONCAT('%',?1,'%') OR a.email LIKE CONCAT('%',?1,'%'))) OR ?1 = '')", nativeQuery = true)
    List<Account> getAccount(String keySearch) throws Exception;

    /**
     * @param fullname,email,dateOfBirth,phone,id
     * @param email
     */
    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE account a SET a.full_name = ?1, a.email = ?2, a.date_of_birth = ?3, a.phone = ?4 WHERE a.id = ?5", nativeQuery = true)
    void editProfile(String fullname, String email, Date dateOfBirth, String phone, Integer id);

    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE account a SET a.is_deleted = 1 WHERE a.id = ?1", nativeQuery = true)
    void deleteAccount(Integer id);


    @Modifying(clearAutomatically = true)
    @Query(value = "{CALL sp_c_create_account(:userName, :passWord, :Email, :dateOfBirth, :fullName, :Phone)}", nativeQuery = true)
    void createAccount(@Param("userName") String username,
                       @Param("passWord") String password,
                       @Param("Email") String Email,
                       @Param("dateOfBirth") Date dateOfBirth,
                       @Param("fullName") String fullName,
                       @Param("Phone") String Phone);
}
