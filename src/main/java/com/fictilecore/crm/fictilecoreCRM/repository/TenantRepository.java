package com.fictilecore.crm.fictilecoreCRM.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.fictilecore.crm.fictilecoreCRM.entity.Tenant;

import java.util.Optional;

public interface TenantRepository extends JpaRepository<Tenant, Long> {
    Optional<Tenant> findByGstNumber(String gstNumber);
    Optional<Tenant> findByCinNumber(String cinNumber);
}
