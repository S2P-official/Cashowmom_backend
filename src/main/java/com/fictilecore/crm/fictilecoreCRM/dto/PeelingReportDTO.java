package com.fictilecore.crm.fictilecoreCRM.dto;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class PeelingReportDTO {
  private Long id;

    private LocalDate date;
    private String lotMark;
    private String origin; // e.g., GHANA
    private String size;   // e.g., A,B,C,D
    private Double issuedWholes;
    private Double issuedBroken;
    private Double totalIssued;
    private Double WWPeeledKgs;
    private Double AddTestKgs;
    private Double UnPeeledKgs;
    private Double BrokenAfterPeeling;
    private Double Sau_Pl;
    private Double RejectionAfterPeeled;
    private Double Husk;
    private Long tenantId;
    private Long employeeId;
    private Double TotalPeelingResultl;

}