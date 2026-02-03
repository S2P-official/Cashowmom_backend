package com.fictilecore.crm.fictilecoreCRM.dto;

import java.time.LocalDate;

import lombok.Getter;
@Getter
public class PeelingReportResponse {

   private Long id;

   private LocalDate date;
   private String lotMark;
   private String origin; // e.g., GHANA
   private String size; // e.g., A,B,C,D
   private Double issuedWholes;
   private Double issuedBroken;
   private Double totalIssued;
   private Double wwPeeledKgs;
   private Double addTestKgs;
   private Double unPeeledKgs;
   private Double brokenAfterPeeling;
   private Double sauPl;
   private Double rejectionAfterPeeled;
   private Double husk;
   private String status;
   private final String employeeName;

   public PeelingReportResponse(

         Long id,
         LocalDate date,
         String lotMark,
         String origin, // e.g., GHANA
         String size, // e.g., A,B,C,D
         Double issuedWholes,
         Double issuedBroken,
         Double totalIssued,
         Double wwPeeledKgs,
         Double addTestKgs,
         Double unPeeledKgs,
         Double brokenAfterPeeling,
         Double sauPl,
         Double rejectionAfterPeeled,
         Double husk,
         String status,
         String employeeName) {

      this.id = id;
      this.date = date;
      this.lotMark = lotMark;
      this.origin = origin;
      this.size = size;
      this.issuedWholes = issuedWholes;
      this.issuedBroken = issuedBroken;
      this.totalIssued = totalIssued;
      this.wwPeeledKgs = wwPeeledKgs;
      this.addTestKgs = addTestKgs;
      this.unPeeledKgs = unPeeledKgs;
      this.brokenAfterPeeling = brokenAfterPeeling;
      this.sauPl = sauPl;
      this.rejectionAfterPeeled = rejectionAfterPeeled;
      this.husk = husk;
      this.status = status;
      this.employeeName = employeeName;
   }
}
