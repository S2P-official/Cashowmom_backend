package com.fictilecore.crm.fictilecoreCRM.dto;

import lombok.*;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShellingReportResponseDTO {

    private LocalDate date;
    private String lotMark;
    private String origin;
    private String sizeRange;
    private Double productionQty;
    private String totalRoasted;
    private String moistureAfterRoasting;
    private String cuttingLine;
    private String wholes;
    private String broken;
    private String rejection;
    private String total;
}
