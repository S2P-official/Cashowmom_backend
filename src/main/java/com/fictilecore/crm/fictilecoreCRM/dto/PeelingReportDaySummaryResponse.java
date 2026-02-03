package com.fictilecore.crm.fictilecoreCRM.dto;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class PeelingReportDaySummaryResponse {

    private final LocalDate date;
    private final Double totalIssued; // total issued kgs for the day
    private final Map<String, Double> sizeWiseTotalIssued; // totals grouped by size
    private final List<PeelingReportDTO> reports; // detailed reports

    public PeelingReportDaySummaryResponse(
            LocalDate date,
            Double totalIssued,
            Map<String, Double> sizeWiseTotalIssued,
            List<PeelingReportDTO> reports
    ) {
        this.date = date;
        this.totalIssued = totalIssued;
        this.sizeWiseTotalIssued = sizeWiseTotalIssued;
        this.reports = reports;
    }

    public LocalDate getDate() {
        return date;
    }

    public Double getTotalIssued() {
        return totalIssued;
    }

    public Map<String, Double> getSizeWiseTotalIssued() {
        return sizeWiseTotalIssued;
    }

    public List<PeelingReportDTO> getReports() {
        return reports;
    }
}
