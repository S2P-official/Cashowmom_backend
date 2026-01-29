package com.fictilecore.crm.fictilecoreCRM.dto;

import lombok.*;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CalibrationReportDTO {

    private Long id;
    private LocalDate date;
    private String lotMark;
    private String origin;

    private Double perBagWeight;
    private String sizeRange;
    private Double noOfBags;

    private Double productionQty;
    private Double totalProductionMts;
    private Double percentage;
    private Double countPerKg;

    private Long tenantId;
    private Long employeeId;
}
