package com.example.ecommerce.repository;

import com.example.ecommerce.model.ShipDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ShipDetailRepo extends JpaRepository<ShipDetail, Integer> {
    @Query("SELECT sd FROM ShipDetail sd WHERE sd.accountId=?1 AND sd.isDeleted = 0")
    List<ShipDetail> findByAccountId(Integer accountId);

    @Query("SELECT sd FROM ShipDetail sd WHERE sd.accountId=?1")
    List<ShipDetail> findByAccountIdAdmin(Integer accountId);
    @Query("SELECT sd FROM ShipDetail sd WHERE sd.accountId=?1 AND sd.isDeleted = 0 AND sd.isDefault=1")
    ShipDetail findByAccountIdAndIsDefault(Integer accountId);

    @Query("SELECT sd FROM ShipDetail sd WHERE sd.fullName=?1 AND sd.isDeleted = 0 AND sd.phone=?2 AND sd.address=?3")
    ShipDetail findByFullNameAndPhoneAndAddress(String fullName,String phone,String address);
}
