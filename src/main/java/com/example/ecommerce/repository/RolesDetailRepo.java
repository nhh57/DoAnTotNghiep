package com.example.ecommerce.repository;

import com.example.ecommerce.model.RolesDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RolesDetailRepo extends JpaRepository<RolesDetail,Integer> {
    @Query("SELECT r FROM RolesDetail r WHERE r.accountId=?1")
    List<RolesDetail> findByAccountId(Integer accountId);

    @Query("SELECT r FROM RolesDetail r WHERE r.accountId=?1 AND r.roleId=?2")
    RolesDetail findByAccountIdAndRoleId(Integer accountId,Integer roleId);
}
