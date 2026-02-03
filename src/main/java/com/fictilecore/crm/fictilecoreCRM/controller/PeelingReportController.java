package com.fictilecore.crm.fictilecoreCRM.controller;
import com.fictilecore.crm.fictilecoreCRM.dto.CalibrationReportResponse;
import com.fictilecore.crm.fictilecoreCRM.dto.PeelingReportDTO;
import com.fictilecore.crm.fictilecoreCRM.dto.PeelingReportDaySummaryResponse;
import com.fictilecore.crm.fictilecoreCRM.dto.PeelingReportResponse;
import com.fictilecore.crm.fictilecoreCRM.entity.PeelingReport;
import com.fictilecore.crm.fictilecoreCRM.service.PeelingReportService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/peeling-reports")
@CrossOrigin(origins = "*")
public class PeelingReportController {

    @Autowired
    private PeelingReportService peelingReportService;

    // -------------------- CREATE REPORT --------------------
  @PostMapping("/tenant/{tenantId}/employee/{employeeId}")
    public List<PeelingReport> createReport(
            @PathVariable Long tenantId,
            @PathVariable(required = false) Long employeeId,
            @RequestBody List<PeelingReport> report
    ) {
        return peelingReportService.createReports(tenantId, employeeId, report);
    }
   
    // -------------------- GET ALL BY TENANT --------------------
    @GetMapping("/tenant/{tenantId}")
    public List<PeelingReport> getReportsByTenant(@PathVariable Long tenantId) {
        return peelingReportService.getReportsByTenant(tenantId);
    }

    // -------------------- GET BY EMPLOYEE --------------------
    @GetMapping("/tenant/{tenantId}/employee/{employeeId}")
    public List<PeelingReportDTO> getReportsByEmployee(
            @PathVariable Long tenantId,
            @PathVariable Long employeeId
    ) {
        return peelingReportService.getReportsByTenantAndEmployee(tenantId, employeeId);
    }

    // -------------------- GET BY DATE --------------------
 @GetMapping("/tenant/{tenantId}/date/{date}")
public PeelingReportDaySummaryResponse getReportsByDate(
        @PathVariable Long tenantId,
        @PathVariable String date
) {
    return peelingReportService.getReportsByTenantAndDate(
            tenantId,
            LocalDate.parse(date)
    );
}


    // -------------------- GET PENDING REPORTS --------------------
    @GetMapping("/tenant/{tenantId}/pending")
public List<PeelingReport> getAllExceptCompletedReports(@PathVariable Long tenantId) {
    return peelingReportService.getAllExceptCompletedReportsByTenant(tenantId);
}


    // -------------------- PATCH REPORT (PARTIAL UPDATE) --------------------
    @PatchMapping("/{id}")
    public PeelingReport patchReport(
            @PathVariable Long id,
            @RequestBody PeelingReport updatedFields
    ) {
        return peelingReportService.patchReport(id, updatedFields);
    }

    // -------------------- DELETE REPORT --------------------
    @DeleteMapping("/{id}")
    public String deleteReport(@PathVariable Long id) {
        peelingReportService.deleteReport(id);
        return "Peeling report deleted successfully";
    }


    @GetMapping
public ResponseEntity<List<PeelingReportResponse>> getReports(
        @RequestParam Long tenantId,
        @RequestParam Long employeeId,
        @RequestParam(required = false)
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        LocalDate date
) {
    List<PeelingReportResponse> reports = peelingReportService
            .getReportsByTenantEmployeeAndDate(tenantId, employeeId, date);
    return ResponseEntity.ok(reports);
}
}
