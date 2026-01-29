package com.fictilecore.crm.fictilecoreCRM.service;

import com.fictilecore.crm.fictilecoreCRM.entity.Tenant;
import com.fictilecore.crm.fictilecoreCRM.entity.enums.PlanType;
import com.fictilecore.crm.fictilecoreCRM.entity.subscription.Subscription;
import com.fictilecore.crm.fictilecoreCRM.repository.SubscriptionRepository;
import com.fictilecore.crm.fictilecoreCRM.repository.TenantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class SubscriptionService {

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @Autowired
    private TenantRepository tenantRepository;

    /**
     * Create a trial subscription for new tenant
     */
    public Subscription createTrialSubscription(Long tenantId, int trialDays) {

        Tenant tenant = tenantRepository.findById(tenantId)
                .orElseThrow(() -> new RuntimeException("Tenant not found"));

        Subscription subscription = Subscription.builder()
                .tenant(tenant)
                .planType(PlanType.TRIAL)
                .startDate(LocalDate.now())
                .endDate(LocalDate.now().plusDays(trialDays))
                .active(true)
                .build();

        return subscriptionRepository.save(subscription);
    }

    /**
     * Validate tenant access before processing any request
     */
    public void validateTenantSubscription(Long tenantId) {

        Subscription subscription = subscriptionRepository
                .findTopByTenant_IdOrderByEndDateDesc(tenantId)
                .orElseThrow(() -> new RuntimeException("No subscription found"));

        if (!subscription.isActive()) {
            throw new RuntimeException("Subscription is inactive");
        }

        if (subscription.getEndDate().isBefore(LocalDate.now())) {
            throw new RuntimeException("Subscription expired");
        }
    }
 

    /**
     * Upgrade or change plan
     */
    public Subscription upgradeSubscription(Long tenantId, PlanType newPlan, int durationDays) {

        Tenant tenant = tenantRepository.findById(tenantId)
                .orElseThrow(() -> new RuntimeException("Tenant not found"));

        // Deactivate old subscriptions
        subscriptionRepository.findTopByTenant_IdOrderByEndDateDesc(tenantId)
                .ifPresent(sub -> {
                    sub.setActive(false);
                    subscriptionRepository.save(sub);
                });

        Subscription newSubscription = Subscription.builder()
                .tenant(tenant)
                .planType(newPlan)
                .startDate(LocalDate.now())
                .endDate(LocalDate.now().plusDays(durationDays))
                .active(true)
                .build();

        return subscriptionRepository.save(newSubscription);
    }

   
}
