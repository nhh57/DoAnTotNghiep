package com.example.ecommerce.repository;
import com.example.ecommerce.model.AccountOauthDataModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AccountOauthRepo extends JpaRepository<AccountOauthDataModel, Long> {

    /**
     * <p>getAccountOauth</p>
     * @param userName
     * @return DAO
     */
    @Query(value = "{CALL sp_g_list_user_roles(:userName)}", nativeQuery = true)
    AccountOauthDataModel getAccountOauth(@Param("userName") String userName) ;
}
