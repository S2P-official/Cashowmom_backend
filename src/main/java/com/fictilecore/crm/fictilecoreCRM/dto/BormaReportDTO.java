package com.fictilecore.crm.fictilecoreCRM.dto;

import lombok.*;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BormaReportDTO {

    private Long id;
    private LocalDate date;

    private String lot;
    private String size;
    private String origin;
    private Double perBag;

    private Double shelledKernelMoisture;
    private String afterBormaKernalMoisture;
    private String aormaTimeDuration;
    private Double bormaTemperature;

    private Double wholesReceived;
    private Double brokensReceived;
    private Double totalReceived;

    private Double wholesFinalafterborama;
    private Double wholesShortafterborama;

    private Double wholesPercent;
    private Double wholesShortPercent;

    private Double brokensFinal;
    private Double brokensShort;

    private Double brokensPercent;
    private Double brokensShortPercent;

    private Double totalIssued;
    private Double totalFinal;
    private Double totalShort;

    private Double totalPercent;
    private Double totalShortPercent;

    private Long tenantId;
    private Long employeeId;
    public Double getShortFinalafterborama() {
        throw new UnsupportedOperationException("Unimplemented method 'getShortFinalafterborama'");
    }
}
