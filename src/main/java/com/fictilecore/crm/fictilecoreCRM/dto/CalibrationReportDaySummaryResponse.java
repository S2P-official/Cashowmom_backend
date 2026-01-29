package com.fictilecore.crm.fictilecoreCRM.dto;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class CalibrationReportDaySummaryResponse {

    private final LocalDate date;
    private final Double totalProductionQty;
    private final Map<String, Double> sizeWiseProductionQty;
    private final List<CalibrationReportResponse> reports;

    public CalibrationReportDaySummaryResponse(
            LocalDate date,
            Double totalProductionQty,
            Map<String, Double> sizeWiseProductionQty,
            List<CalibrationReportResponse> reports
    ) {
        this.date = date;
        this.totalProductionQty = totalProductionQty;
        this.sizeWiseProductionQty = sizeWiseProductionQty;
        this.reports = reports;
    }

    public LocalDate getDate() { return date; }
    public Double getTotalProductionQty() { return totalProductionQty; }
    public Map<String, Double> getSizeWiseProductionQty() { return sizeWiseProductionQty; }
    public List<CalibrationReportResponse> getReports() { return reports; }
}
