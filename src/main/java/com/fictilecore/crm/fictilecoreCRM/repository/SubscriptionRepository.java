package com.fictilecore.crm.fictilecoreCRM.repository;

import com.fictilecore.crm.fictilecoreCRM.entity.subscription.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {

    // Get latest subscription of tenant
    Optional<Subscription> findTopByTenant_IdOrderByEndDateDesc(Long tenantId);

    // Check if active subscription exists
    boolean existsByTenant_IdAndActiveTrue(Long tenantId);
}

