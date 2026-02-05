package com.fictilecore.crm.fictilecoreCRM.dto;

import java.time.LocalDate;

import com.fictilecore.crm.fictilecoreCRM.entity.BormaReport;

import lombok.Getter;

@Getter
public class BormaReportResponse {

    private Long id;
    private LocalDate date;

    private String lotMark;
    private String origin;
    private Double perBagWeight;
    private String sizeRange;

    private String afterBormaKernelMoisture;
    private String bormaTimeDuration;
    private String bormaTemperature;

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
            String afterBormaKernelMoisture,
            String bormaTimeDuration,
            String bormaTemperature,
            Double aftrBormaWholes,
            Double aftrBormaBrokens,
            Double shortWholes,
            Double shortBrokens,
            Double totalWholes,
            Double totalShort,
            String employeeName
    ) {
        this.id = id;
        this.date = date;
        this.lotMark = lotMark;
        this.origin = origin;
        this.sizeRange = sizeRange;
        this.afterBormaKernelMoisture = afterBormaKernelMoisture;
        this.bormaTimeDuration = bormaTimeDuration;
        this.bormaTemperature = bormaTemperature;
        this.aftrBormaWholes = aftrBormaWholes;
        this.aftrBormaBrokens = aftrBormaBrokens;
        this.shortWholes = shortWholes;
        this.shortBrokens = shortBrokens;
        this.totalWholes = totalWholes;
        this.totalShort = totalShort;
        this.employeeName = employeeName;
    }

    /* ---------- Mapper ---------- */

    public static BormaReportResponse fromEntity(BormaReport r) {
        if (r == null) return null;

        return new BormaReportResponse(
                r.getId(),
                r.getDate(),
                r.getLotMark(),
                r.getOrigin(),
                r.getSizeRange(),
                r.getAfterBormaKernelMoisture(),
                r.getBormaTimeDuration(),
                r.getBormaTemperature(),
                r.getAftrBormaWholes(),
                r.getAftrBormaBrokens(),
                r.getShortWholes(),
                r.getShortBrokens(),
                r.getTotalWholes(),
                r.getTotalShort(),
                r.getEmployee() != null
                        ? r.getEmployee().getEmployee_name()
                        : null
        );
    }
}
