package com.fictilecore.crm.fictilecoreCRM.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BormaReportDTO {

  

    private String id;
    private String lotMark;
    private String origin; // e.g., GHANA
    private Double perBagWeight;
    private String sizeRange; 
    private Double countPerKg;
    private String status;
    private String cookingTime;
    private String roasterName;
    private String moistureAfterRoasting;
    private String cuttingLine;
    private String AfterBormaKernalMoisture;

    private Double wholes;
    private Double broken;
    private Double rejection;
    private Double uncut;
    private Double partly;
    private Double total;
    private String BormaTimeDuration;
    private String BormaTemperature;
    private Double aftrBormaWholes;
    private Double aftrBormaBrokens;
    private Double shortWholes;
    private Double shortBrokens;
   
}
