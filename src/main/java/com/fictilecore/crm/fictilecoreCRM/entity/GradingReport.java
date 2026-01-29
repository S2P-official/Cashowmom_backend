package com.fictilecore.crm.fictilecoreCRM.entity;

import com.fictilecore.crm.fictilecoreCRM.entity.base.BaseTenantEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "grading_report")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GradingReport extends BaseTenantEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // --- Meta Data ---
    @Column(nullable = false)
    private String lotMark;     // e.g., "Huong"

    @Column(nullable = false)
    private String grade;       // e.g., "W210"

    private String origin;      // e.g., "GHANA"

    // --- Production Data ---
    private Double totalProduction;  // TOTAL PRO
    private Double kgPerBag;         // KG/BAG

    // --- Grading Weights ---
    private Double weightPeeled;     
    private Double broken;           
    private Double underTesta;       
    private Double notDetectedSK1;   
    private Double satWeightBe;      
    private Double fromHusk;         

    // --- Link to Borma Report ---
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "borma_report_id")
    private BormaReport bormaReport;

    // --- Employee ---
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;


    @PrePersist
    protected void onCreate() {
        // Use the getter for tenantId from BaseTenantEntity
        if (super.getTenantId() == null) {
            throw new IllegalStateException(
                "Tenant ID must be set before persisting GradingReport"
            );
        }
    }

    public Object getComments() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getComments'");
    }

    public void setComments(Object comments) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setComments'");
    }
}
