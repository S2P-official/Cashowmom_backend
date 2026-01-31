package com.fictilecore.crm.fictilecoreCRM.dto;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class BormaReportDaySummaryResponse {

    private final LocalDate date;
    private final Double totalWholes;
    private final Map<String, Double> sizeWiseProductionQty;
    private final List<BormaReportResponse> reports;

    public BormaReportDaySummaryResponse(
            LocalDate date,
            Double totalWholes,
            Map<String, Double> sizeWiseProductionQty,
            List<BormaReportResponse> reports
    ) {
        this.date = date;
        this.totalWholes = totalWholes;
        this.sizeWiseProductionQty = sizeWiseProductionQty;
        this.reports = reports;
    }

    public LocalDate getDate() { return date; }
    public Double getTotalProductionQty() { return totalWholes; }
    public Map<String, Double> getSizeWiseProductionQty() { return sizeWiseProductionQty; }
    public List<BormaReportResponse> getReports() { return reports; }
}
