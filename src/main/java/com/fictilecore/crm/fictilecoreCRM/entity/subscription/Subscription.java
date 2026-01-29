package com.fictilecore.crm.fictilecoreCRM.entity.subscription;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

import com.fictilecore.crm.fictilecoreCRM.entity.Tenant;
import com.fictilecore.crm.fictilecoreCRM.entity.enums.PlanType;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Subscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Each subscription belongs to one tenant
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tenant_id", nullable = false)
    private Tenant tenant;

    /**
     * Subscription plan
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PlanType planType;

    /**
     * Subscription validity
     */
    @Column(nullable = false)
    private LocalDate startDate;

    @Column(nullable = false)
    private LocalDate endDate;

    /**
     * Soft activation flag
     */
    @Column(nullable = false)
    private boolean active;
}
