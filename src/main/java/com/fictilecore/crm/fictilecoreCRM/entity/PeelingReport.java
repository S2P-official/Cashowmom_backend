package com.fictilecore.crm.fictilecoreCRM.entity;

import com.fictilecore.crm.fictilecoreCRM.entity.base.BaseTenantEntity;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "peeling_report")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PeelingReport extends BaseTenantEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate date;
    private String lotMark;
    private String origin; // e.g., GHANA
    private String size;   // e.g., A,B,C,D
    private Double issuedWholes;
    private Double issuedBroken;
    private Double totalIssued;
    private Double moisture;

    // --- Whole / Broken details ---
    private Double wwPeeledKgs;
    private Double wwPeeledPercent;

    private Double addTestaKgs;
    private Double addTestaPercent;

    private Double unpeeledKgs;
    private Double unpeeledPercent;

    private Double brokensKgs;
    private Double brokensPercent;

    private Double sauPlKgs;
    private Double sauPlPercent;

    private Double rejectionKgs;

    private Double totalKgs;
    private Double totalPercent;

    // --- Unpeeled & Testa Work ---
    private Double unpeeledReceived;
    private Double testaReceived;

    // --- Unpeeled Work & Machine Settings ---
    private Double unpeeledWholes;
    private Double unpeeledBroken;
    private Double unpeeledRejection;
    private Double unpeeledTotal;

    private Double shaft;
    private Double peelerDrumSpeed;
    private Double airPeeler;
    private Double airPressure;

    // --- Grading Summary ---
    private Double wholeTotal;
    private Double wholePercent;

    private Double brokenTotal;
    private Double brokenPercent;
    private Double brokenSubTotal;

    private Double rejectionTotal;
    private Double rejectionPercent;

    private Double husk;
    private Double huskPercent;

    private Double grandTotal;
    private Double gradeDiff;
    private Double huskShortPercent;

    // --- Relations ---
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    @PrePersist
    protected void onCreate() {
        if (getTenantId() == null) {
            throw new IllegalStateException(
                "Tenant ID must be set before persisting PeelingReport"
            );
        }
    }
}
