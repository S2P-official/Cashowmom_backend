package com.fictilecore.crm.fictilecoreCRM.dto;

import java.time.LocalDate;

public class CalibrationReportResponse {

    private final Long id;
    private final LocalDate date;
    private final String lotMark;
    private final String origin;
    private final Double perBagWeight;
    private final String sizeRange;
    private final Double noOfBags;
    private final Double productionQty;
    private final Double countPerKg;
    private final String employeeName;

    public CalibrationReportResponse(
            Long id,
            LocalDate date,
            String lotMark,
            String origin,
            Double perBagWeight,
            String sizeRange,
            Double noOfBags,
            Double productionQty,
            Double countPerKg,
            String employeeName
    ) {
        this.id = id;
        this.date = date;
        this.lotMark = lotMark;
        this.origin = origin;
        this.perBagWeight = perBagWeight;
        this.sizeRange = sizeRange;
        this.noOfBags = noOfBags;
        this.productionQty = productionQty;
        this.countPerKg = countPerKg;
        this.employeeName = employeeName;
    }

    public Long getId() { return id; }
    public LocalDate getDate() { return date; }
    public String getLotMark() { return lotMark; }
    public String getOrigin() { return origin; }
    public Double getPerBagWeight() { return perBagWeight; }
    public String getSizeRange() { return sizeRange; }
    public Double getNoOfBags() { return noOfBags; }
    public Double getProductionQty() { return productionQty; }
    public Double getCountPerKg() { return countPerKg; }
    public String getEmployeeName() { return employeeName; }
}
