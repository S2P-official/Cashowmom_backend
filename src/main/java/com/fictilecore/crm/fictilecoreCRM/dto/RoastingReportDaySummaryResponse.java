package com.fictilecore.crm.fictilecoreCRM.dto;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RoastingReportDaySummaryResponse {

    private LocalDate date;

    // 1️⃣ Total roasted for the day
    private Double totalRoastedSum;

    // 2️⃣ Cutting-line-wise total roasted
    private Map<String, Double> cuttingLineWiseTotalRoasted;

    // 3️⃣ Individual rows
    private List<RoastingReportResponse> reports;
}
    