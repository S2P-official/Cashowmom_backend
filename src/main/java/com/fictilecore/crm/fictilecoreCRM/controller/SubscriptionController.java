package com.fictilecore.crm.fictilecoreCRM.controller;

import com.fictilecore.crm.fictilecoreCRM.entity.enums.PlanType;
import com.fictilecore.crm.fictilecoreCRM.entity.subscription.Subscription;
import com.fictilecore.crm.fictilecoreCRM.service.SubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/subscriptions")
public class SubscriptionController {

    @Autowired
    private SubscriptionService subscriptionService;

    /**
     * Create trial subscription for a tenant
     */
    @PostMapping("/trial")
    public ResponseEntity<Subscription> createTrialSubscription(
            @RequestParam Long tenantId,
            @RequestParam(defaultValue = "29") int trialDays
    ) {
        Subscription subscription =
                subscriptionService.createTrialSubscription(tenantId, trialDays);
        return ResponseEntity.ok(subscription);
    }

    /**
     * Validate tenant subscription (can be used as pre-check)
     */
    @GetMapping("/validate/{tenantId}")
    public ResponseEntity<String> validateSubscription(@PathVariable Long tenantId) {
        subscriptionService.validateTenantSubscription(tenantId);
        return ResponseEntity.ok("Subscription is valid");
    }

    /**
     * Upgrade or change subscription plan
     */
    @PostMapping("/upgrade")
    public ResponseEntity<Subscription> upgradeSubscription(
            @RequestParam Long tenantId,
            @RequestParam PlanType planType,
            @RequestParam int durationDays
    ) {
        Subscription subscription =
                subscriptionService.upgradeSubscription(tenantId, planType, durationDays);
        return ResponseEntity.ok(subscription);
    }
}

