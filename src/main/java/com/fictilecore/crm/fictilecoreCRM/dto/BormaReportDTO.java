package com.fictilecore.crm.fictilecoreCRM.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

import lombok.AllArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BormaReportDTO {

    private Long id;
       private LocalDate date;
    private String lotMark;
    private String origin; // e.g., GHANA
    private Double perBagWeight;
    private String sizeRange;
    private String status;
    private String AfterBormaKernalMoisture;
    private String BormaTimeDuration;
    private String BormaTemperature;
    private Double aftrBormaWholes;
    private Double aftrBormaBrokens;
    private Double shortWholes;
    private Double shortBrokens;

}
