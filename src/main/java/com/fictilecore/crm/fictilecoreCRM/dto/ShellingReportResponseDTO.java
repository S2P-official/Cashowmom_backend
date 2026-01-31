package com.fictilecore.crm.fictilecoreCRM.dto;

import com.fictilecore.crm.fictilecoreCRM.entity.ShellingReport;
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

    // ✅ THIS IS WHAT WAS MISSING
    public static ShellingReportResponseDTO fromEntity(ShellingReport e) {
        return ShellingReportResponseDTO.builder()
                .date(e.getDate())
                .lotMark(e.getLotMark())
                .origin(e.getOrigin())
                .sizeRange(e.getSizeRange())
                .productionQty(e.getProductionQty())
                .totalRoasted(
                        e.getTotalRoasted() != null
                                ? e.getTotalRoasted().toString()
                                : null
                )
                .moistureAfterRoasting(e.getMoistureAfterRoasting())
                .cuttingLine(e.getCuttingLine())
                .wholes(
                        e.getWholes() != null
                                ? e.getWholes().toString()
                                : null
                )
                .broken(
                        e.getBroken() != null
                                ? e.getBroken().toString()
                                : null
                )
                .rejection(
                        e.getRejection() != null
                                ? e.getRejection().toString()
                                : null
                )
                .total(
                        e.getTotal() != null
                                ? e.getTotal().toString()
                                : null
                )
                .build();
    }
}
