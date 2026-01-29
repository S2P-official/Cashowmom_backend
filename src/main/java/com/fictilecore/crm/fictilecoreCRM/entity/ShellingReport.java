package com.fictilecore.crm.fictilecoreCRM.entity;

import com.fictilecore.crm.fictilecoreCRM.entity.base.BaseTenantEntity;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "shelling_report")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShellingReport extends BaseTenantEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate date;

    private String lotMark;
    private String origin; // e.g., GHANA
    private Double perBagWeight;
    private String sizeRange; // e.g., "18<", "18-20", etc.
    private Double noOfBags;
    private Double productionQty;
    private Double totalProductionMts;
    private Double percentage;
    private Double countPerKg;
    private String status;
    private String cookingTime;
    private String dryRcnMoisture;
    private String roasterName;
    private String tempForVnMachine;
    private String roastingDuration;
    private String soackingMoisture;
    private String moistureAfterRoasting;
    private Integer totalRoasted;
    private String cuttingLine;

    private Integer wholes;
    private Integer broken;
    private Integer rejection;
    private Integer uncut;
    private Integer partly;
    private Integer total;

    // --- Employee ---
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;





    // --- PrePersist checks ---
    @PrePersist
    protected void prePersist() {
        if (this.date == null) {
            this.date = LocalDate.now();
        }
        if (getTenantId() == null) {
            throw new IllegalStateException(
                "Tenant ID must be set before persisting ShellingReport"
            );
        }
    }
}
