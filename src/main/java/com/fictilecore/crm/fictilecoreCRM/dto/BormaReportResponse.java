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
    private String AfterBormaKernalMoisture;
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
            String sizeRange,
            String AfterBormaKernalMoisture,
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
        this.AfterBormaKernalMoisture = AfterBormaKernalMoisture;
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
