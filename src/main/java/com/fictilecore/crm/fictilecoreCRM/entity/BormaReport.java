package com.fictilecore.crm.fictilecoreCRM.entity;

import com.fictilecore.crm.fictilecoreCRM.entity.base.BaseTenantEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "borma_report")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BormaReport extends BaseTenantEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false)
    private String lot;

    @Column(name = "size")
    private String size;

    private String origin;
    private Double perBag;

    private Double shelledKernelMoisture;
    private String afterBormaKernelMoisture;
    private String bormaTimeDuration;
    private Double bormaTemperature;

    private Double wholesReceived;
    private Double brokensReceived;

    private Double wholesCountAfterBorma;
    private Double wholesShortCountAfterBorma;

    private Double wholesFinalAfterBormaPercent;
    private Double wholesShortAfterBormaPercent;

    private Double brokensCountAfterBorma;
    private Double brokensShortCountAfterBorma;
    private Double totalPercentOfBrokensAfterBorma;
    private Double totalPercentOfShortBrokensAfterBorma;

    private Double totalIssued;
    private Double totalFinal;
    private Double totalShort;
    private Double totalPercent;
    private Double totalShortPercent;

    @Column(nullable = false)
    private String status;



    // --- Employee association (Tenant derived from BaseTenantEntity) ---
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    @PrePersist
    protected void onCreate() {
        if (this.date == null) {
            this.date = LocalDate.now();
        }
    }
}
