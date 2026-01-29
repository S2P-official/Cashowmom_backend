package com.fictilecore.crm.fictilecoreCRM.mapper;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.Map;

import com.fictilecore.crm.fictilecoreCRM.dto.ShellingReportDTO;

@Data
@AllArgsConstructor
public class MonthlyShellingResponse {
    private String month;
    private Map<String, List<ShellingReportDTO>> reports;

    public MonthlyShellingResponse(int year, int month, Map<String, List<ShellingReportDTO>> reports) {
        this.month = year + "-" + String.format("%02d", month);
        this.reports = reports;
    }
}
