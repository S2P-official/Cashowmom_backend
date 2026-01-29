package com.fictilecore.crm.fictilecoreCRM.entity;

import com.fictilecore.crm.fictilecoreCRM.entity.base.BaseTenantEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "calibration_size_detail")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CalibrationSizeDetail extends BaseTenantEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String sizeRange;         // e.g., "18<", "18-20", "20-22"
    private Double numberOfBags;
    private Double productionQty;
    private Double oldMachineQty;
    private Double totalProduction;
    private Double percentage;
    private Double countPerKg;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "report_id", nullable = false)
    private CalibrationReport report;

    @PrePersist
    protected void prePersist() {
        if (getTenantId() == null) {
            throw new IllegalStateException(
                "Tenant ID must be set before persisting CalibrationSizeDetail"
            );
        }
    }
}
