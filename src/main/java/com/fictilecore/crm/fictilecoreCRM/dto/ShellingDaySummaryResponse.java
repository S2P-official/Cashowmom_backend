package com.fictilecore.crm.fictilecoreCRM.dto;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class ShellingDaySummaryResponse {

    private LocalDate date;
    private int totalWholes;
    private int totalBrokens;
    private Map<String, Integer> cuttingLineWiseTotals;
    private List<ShellingReportDTO> reports;

    public ShellingDaySummaryResponse(
            LocalDate date,
            int totalWholes,
            int totalBrokens,
            Map<String, Integer> cuttingLineWiseTotals,
            List<ShellingReportDTO> reports
    ) {
        this.date = date;
        this.totalWholes = totalWholes;
        this.totalBrokens = totalBrokens;
        this.cuttingLineWiseTotals = cuttingLineWiseTotals;
        this.reports = reports;
    }

    // getters & setters
}
