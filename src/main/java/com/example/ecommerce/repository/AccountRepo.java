package com.example.ecommerce.repository;

import com.example.ecommerce.model.Account;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

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
//    @Modifying(clearAutomatically = true)
    @Query(value = "SELECT a.id, a.username, a.password, a.email,a.gender, a.date_of_birth, a.full_name, a.phone,  a.cart_id, a.is_deleted, a.created_at, a.updated_at FROM account a WHERE ((?1 <> '' AND (a.username LIKE CONCAT('%',?1,'%') OR a.username LIKE CONCAT('%',?1,'%') OR a.phone LIKE CONCAT('%',?1,'%') OR a.full_name LIKE CONCAT('%',?1,'%') OR a.email LIKE CONCAT('%',?1,'%') )) OR ?1 = '') AND (a.is_deleted >= 0 AND a.is_deleted = ?2) OR (?2 < 0)", nativeQuery = true)
    List<Account> getAllAccount(String keySearch, int isDeleted,int roleId, Pageable pageable) throws Exception;

    /**
     * <p>findOneAccount</p>
     *
     * @param keySearch
     * @return DAO
     */
    @Modifying(clearAutomatically = true)
    @Query(value = "SELECT a.id, a.username, a.password, a.email, a.date_of_birth, a.full_name, a.phone,  a.cart_id, a.is_deleted, a.created_at, a.updated_at FROM account a WHERE ((?1 <> '' AND (a.username LIKE CONCAT('%',?1,'%') OR a.username LIKE CONCAT('%',?1,'%') OR a.phone LIKE CONCAT('%',?1,'%') OR a.full_name LIKE CONCAT('%',?1,'%') OR a.email LIKE CONCAT('%',?1,'%'))) OR ?1 = '')", nativeQuery = true)
    List<Account> getAccount(String keySearch) throws Exception;

    /**
     * @param fullname,email,dateOfBirth,phone,id
     * @param email
     */
    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE account a SET a.full_name = ?1, a.email = ?2, a.date_of_birth = ?3, a.phone = ?4 WHERE a.id = ?5", nativeQuery = true)
    void editProfile(String fullname, String email, Date dateOfBirth, String phone, Integer id);

    /**
     * <p>deleteAccount</p>
     * @param id
     */
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

    @Query("SELECT a FROM Account a WHERE a.username=?1")
    Account findByUsername(String username);

    @Query("SELECT a FROM Account a WHERE a.email=?2 OR a.phone=?2 AND a.username=?1")
    Account findByEmailOrPhone(String username,String emailOrPhone);

    @Query("SELECT ac FROM Account ac WHERE ac.username=?1 OR ac.fullName like %?2% OR ac.email like %?2% OR ac.phone like %?2%")
    List<Account> findByUsernameOrFullNameOrEmailOrPhone(String keySearch1,String keySearch2);

    @Query("SELECT ac FROM Account ac WHERE ac.username=?1 OR ac.fullName like %?2% OR ac.email like %?2% OR ac.phone like %?2%")
    Page<Account> findByUsernameOrFullNameOrEmailOrPhone(Pageable pageable, String keySearch1,String keySearch2);

}
