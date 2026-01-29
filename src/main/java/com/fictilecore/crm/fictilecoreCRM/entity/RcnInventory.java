package com.fictilecore.crm.fictilecoreCRM.entity;

import com.fictilecore.crm.fictilecoreCRM.entity.base.BaseTenantEntity;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "rcn_inventory")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RcnInventory extends BaseTenantEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // --- Basic Details ---
    @Column(name = "date_of_arrival")
    private LocalDate dateOfArrival;

    @Column(name = "bill_of_lading_no")
    private String billOfLadingNo;

    @Column(name = "supplier_name")
    private String supplierName;

    @Column(name = "total_no_of_container")
    private Integer totalNoOfContainer;

    @Column(name = "origin_code")
    private String originCode;

    @Column(name = "total_mts")
    private Double totalMts;

    // --- Quality Details ---
    @Column(name = "nut_count")
    private Double nutCount;

    @Column(name = "moisture")
    private Double moisture;

    @Column(name = "cutting_out_turn_lbs")
    private Double cuttingOutTurnLbs;

    @Column(name = "foreign_matter")
    private Double foreignMatter;

    @Column(name = "container_condition")
    private String containerCondition;

    @Column(name = "packaging_condition")
    private String packagingCondition;

    @Column(name = "appearance_product")
    private String appearanceProduct;

    @Column(name = "pest_contamination")
    private String pestContamination;

    @Column(name = "status")
    private String status;

    // --- Employee ---
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    // --- PrePersist Checks ---
    @PrePersist
    protected void prePersist() {
        if (getTenantId() == null) {
            throw new IllegalStateException(
                "Tenant ID must be set before persisting RcnInventory"
            );
        }
        if (this.dateOfArrival == null) {
            this.dateOfArrival = LocalDate.now();
        }
    }
}
