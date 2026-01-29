package com.fictilecore.crm.fictilecoreCRM.dto;

import java.time.LocalDate;

public record BormaReportRowDTO(
        Long id,
        LocalDate date,
        String lot,
        String origin,
        Double wholesReceived,
        Double brokensReceived,
        Double wholesFinal,
        Double brokensFinal,
        Double totalFinal,
        Double totalPercent,
        String status,
        String employeeName
) {
    public BormaReportRowDTO {
        // validation or transformation allowed here
    }
}

