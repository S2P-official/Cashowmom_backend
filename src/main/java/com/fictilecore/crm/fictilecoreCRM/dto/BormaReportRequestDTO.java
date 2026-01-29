package com.fictilecore.crm.fictilecoreCRM.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class BormaReportRequestDTO {

    private LocalDate date;
    private String lot;
    private String size;
    private String origin;
    private Double perBag;
    private Double shelledKernelMoisture;
    private String afterBormaKernalMoisture;
    private String aormaTimeDuration;
    private Double bormaTemperature;

    private Double wholesReceived;
    private Double brokensReceived;

    private Double wholesCountAfterborama;
    private Double wholesShortCountAfterborama;

    private Double brokensCountAfterBorma;
    private Double brokensShortCountAfterBorma;
}
