package com.fictilecore.crm.fictilecoreCRM.dto;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class RoastingReportDTO {
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

    private String totalRoasted;

    private String CuttingLine;
}
