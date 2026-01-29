package com.fictilecore.crm.fictilecoreCRM.controller;

import com.fictilecore.crm.fictilecoreCRM.dto.RoastingReportDTO;
import com.fictilecore.crm.fictilecoreCRM.dto.RoastingReportDaySummaryResponse;
import com.fictilecore.crm.fictilecoreCRM.entity.RoastingReport;
import com.fictilecore.crm.fictilecoreCRM.service.RoastingReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/roasting-reports")
@CrossOrigin(origins = "*")
public class RoastingReportController {

    @Autowired
    private RoastingReportService roastingReportService;

    // -------------------- CREATE SINGLE REPORT --------------------
    @PostMapping("/tenant/{tenantId}/employee/{employeeId}")
    public RoastingReport createReport(
            @PathVariable Long tenantId,
            @PathVariable(required = false) Long employeeId,
            @RequestBody RoastingReport report
    ) {
        return roastingReportService.createReport(tenantId, employeeId, report);
    }

    // -------------------- GET ALL REPORTS FOR A TENANT --------------------
    @GetMapping("/tenant/{tenantId}")
    public List<RoastingReport> getReportsByTenant(@PathVariable Long tenantId) {
        return roastingReportService.getReportsByTenant(tenantId);
    }

    // -------------------- GET REPORTS FOR A SPECIFIC EMPLOYEE (DTO) --------------------
    @GetMapping("/tenant/{tenantId}/employee/{employeeId}")
    public List<RoastingReportDTO> getReportsByEmployee(
            @PathVariable Long tenantId,
            @PathVariable Long employeeId
    ) {
        return roastingReportService.getReportsByTenantAndEmployee(tenantId, employeeId);
    }

    // -------------------- GET REPORTS BY DATE --------------------
    @GetMapping("/tenant/{tenantId}/date/{date}")
    public RoastingReportDaySummaryResponse getReportsByDate(
            @PathVariable Long tenantId,
            @PathVariable String date
    ) {
        return roastingReportService.getReportsByDate(
                tenantId,
                LocalDate.parse(date)
        );
    }

    // -------------------- DELETE REPORT --------------------
    @DeleteMapping("/tenant/{tenantId}/report/{reportId}")
    public String deleteReport(
            @PathVariable Long tenantId,
            @PathVariable Long reportId
    ) {
        roastingReportService.deleteReport(tenantId, reportId);
        return "Roasting report deleted successfully";
    }

    // -------------------- PATCH REPORT (Partial Update) --------------------
    @PatchMapping("/tenant/{tenantId}/report/{reportId}")
    public RoastingReport patchReport(
            @PathVariable Long tenantId,
            @PathVariable Long reportId,
            @RequestBody RoastingReport updatedFields
    ) {
        return roastingReportService.patchReport(tenantId, reportId, updatedFields);
    }

    // -------------------- GET PENDING REPORTS (status != Completed) --------------------
    @GetMapping("/tenant/{tenantId}/pending")
    public List<RoastingReport> getAllPendingReports(@PathVariable Long tenantId) {
        return roastingReportService.getAllExceptCompletedReportsByTenant(tenantId);
    }
}
