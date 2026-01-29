package com.fictilecore.crm.fictilecoreCRM.entity;


import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

import com.fictilecore.crm.fictilecoreCRM.entity.base.BaseTenantEntity;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CalibrationReport extends BaseTenantEntity{

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

    private Double percentage;

    private Double countPerKg;

    private String status;


    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;
}

