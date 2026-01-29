package com.fictilecore.crm.fictilecoreCRM.dto;

import lombok.Data;

@Data
public class BormaReportResponseDTO {

    private Long id;

    private Double wholesFinalafterboramaPercent;
    private Double wholesShortafterboramaPercent;

    private Double totalPercentOfBrokensAfterBorma;
    private Double totalPercentOfShortBrokensAfterBorma;

    private Double totalIssued;
    private Double totalFinal;
    private Double totalShort;

    private Double totalPercent;
    private Double totalShortPercent;
}
