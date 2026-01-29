package com.fictilecore.crm.fictilecoreCRM.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
public class BormaDaySummaryResponse {

    private LocalDate date;

    // Day-level totals
    private Double totalIssued;
    private Double totalFinal;
    private Double totalShort;

    private Double totalPercent;
    private Double totalShortPercent;

    // Table rows
    private List<BormaReportResponseDTO> reports;
}
