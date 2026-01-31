package com.fictilecore.crm.fictilecoreCRM.dto;

import java.time.LocalDate;

import com.fictilecore.crm.fictilecoreCRM.entity.RoastingReport;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RoastingReportResponse {

    private Long id;
    private LocalDate date;

    private String lotMark;
    private String origin;
    private Double perBagWeight;
    private String sizeRange;
    private Double noOfBags;
    private Double productionQty;
    private Double totalProductionMts;
    private Double percentage;
    private Double countPerKg;
    private String status;

    private String cookingTime;
    private String dryRcnMoisture;
    private String roasterName;
    private String tempForVnMachine;
    private String roastingDuration;
    private String soackingMoisture;
    private String moistureAfterRoasting;
    private String totalRoasted;
    private String cuttingLine;

    private String employeeName;

    public static RoastingReportResponse fromEntity(RoastingReport r) {
        if (r == null) return null;

        RoastingReportResponse res = new RoastingReportResponse();

        res.setId(r.getId());
        res.setDate(r.getDate());

        res.setLotMark(r.getLotMark());
        res.setOrigin(r.getOrigin());
        res.setPerBagWeight(r.getPerBagWeight());
        res.setSizeRange(r.getSizeRange());
        res.setNoOfBags(r.getNoOfBags());
        res.setProductionQty(r.getProductionQty());
        res.setTotalProductionMts(r.getTotalProductionMts());
        res.setPercentage(r.getPercentage());
        res.setCountPerKg(r.getCountPerKg());
        res.setStatus(r.getStatus());

        res.setCookingTime(r.getCookingTime());
        res.setDryRcnMoisture(r.getDryRcnMoisture());
        res.setRoasterName(r.getRoasterName());
        res.setTempForVnMachine(r.getTempForVnMachine());
        res.setRoastingDuration(r.getRoastingDuration());
        res.setSoackingMoisture(r.getSoackingMoisture());
        res.setMoistureAfterRoasting(r.getMoistureAfterRoasting());
        res.setTotalRoasted(r.getTotalRoasted());
        res.setCuttingLine(r.getCuttingLine());

        if (r.getEmployee() != null) {
            res.setEmployeeName(r.getEmployee().getEmployee_name());
        }

        return res;
    }
}
