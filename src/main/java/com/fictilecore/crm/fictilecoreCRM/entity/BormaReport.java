package com.fictilecore.crm.fictilecoreCRM.entity;

import java.time.LocalDate;

import com.fictilecore.crm.fictilecoreCRM.entity.base.BaseTenantEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class BormaReport extends BaseTenantEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String lotMark;
    private String origin;
    private String sizeRange; 
    private String status;
    private String afterBormaKernelMoisture;
    private String bormaTimeDuration;
    private String bormaTemperature;
    private Double aftrBormaWholes;
    private Double aftrBormaBrokens;
    private Double shortWholes;
    private Double shortBrokens;
    private Double totalWholes;
    private Double totalShort;

    

        @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;
        private LocalDate date;

        // --- PrePersist checks ---
    @PrePersist
    protected void prePersist() {
        if (this.date == null) {
            this.date = LocalDate.now();
        }
        if (getTenantId() == null) {
            throw new IllegalStateException(
                "Tenant ID must be set before persisting BormaReport"
            );
        }
    }


}
