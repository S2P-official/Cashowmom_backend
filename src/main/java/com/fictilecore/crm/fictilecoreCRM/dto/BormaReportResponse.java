package com.fictilecore.crm.fictilecoreCRM.dto;

import java.time.LocalDate;

import lombok.Getter;

@Getter
public class BormaReportResponse {

    private Long id;
    private LocalDate date;

    private String lotMark;
    private String origin;
    private Double perBagWeight;
    private String sizeRange;
    private Double countPerKg;

    private String cookingTime;
    private String roasterName;
    private String moistureAfterRoasting;
    private String cuttingLine;
    private String AfterBormaKernalMoisture;

    private Double wholes;
    private Double broken;
    private Double rejection;
    private Double uncut;
    private Double partly;
    private Double total;

    private String BormaTimeDuration;
    private String BormaTemperature;

    private Double aftrBormaWholes;
    private Double aftrBormaBrokens;
    private Double shortWholes;
    private Double shortBrokens;

    private Double totalWholes;
    private Double totalShort;

    private Double noOfBags;
    private String employeeName;

    /* ---------- Constructor ---------- */

    public BormaReportResponse(
            Long id,
            LocalDate date,
            String lotMark,
            String origin,
            Double perBagWeight,
            String sizeRange,
            Double countPerKg,
            String cookingTime,
            String roasterName,
            String moistureAfterRoasting,
            String cuttingLine,
            String AfterBormaKernalMoisture,
            Double wholes,
            Double broken,
            Double rejection,
            Double uncut,
            Double partly, 
            Double total,
            String BormaTimeDuration,
            String BormaTemperature,
            Double aftrBormaWholes,
            Double aftrBormaBrokens,
            Double shortWholes,
            Double shortBrokens,
            Double totalWholes,
            Double totalShort,
            Double noOfBags,
            String employeeName
    ) {
        this.id = id;
        this.date = date;
        this.lotMark = lotMark;
        this.origin = origin;
        this.perBagWeight = perBagWeight;
        this.sizeRange = sizeRange;
        this.countPerKg = countPerKg;
        this.cookingTime = cookingTime;
        this.roasterName = roasterName;
        this.moistureAfterRoasting = moistureAfterRoasting;
        this.cuttingLine = cuttingLine;
        this.AfterBormaKernalMoisture = AfterBormaKernalMoisture;
        this.wholes = wholes;
        this.broken = broken;
        this.rejection = rejection;
        this.uncut = uncut;
        this.partly = partly;
        this.total = total;
        this.BormaTimeDuration = BormaTimeDuration;
        this.BormaTemperature = BormaTemperature;
        this.aftrBormaWholes = aftrBormaWholes;
        this.aftrBormaBrokens = aftrBormaBrokens;
        this.shortWholes = shortWholes;
        this.shortBrokens = shortBrokens;
        this.totalWholes = totalWholes;
        this.totalShort = totalShort;
        this.noOfBags = noOfBags;
        this.employeeName = employeeName;
    }

    /* ---------- Getters ---------- */

}
