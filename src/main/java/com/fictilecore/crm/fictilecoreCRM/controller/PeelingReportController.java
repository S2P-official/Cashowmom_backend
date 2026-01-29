package com.fictilecore.crm.fictilecoreCRM.controller;

import com.fictilecore.crm.fictilecoreCRM.entity.PeelingReport;
import com.fictilecore.crm.fictilecoreCRM.service.PeelingReportService;

import org.springframework.beans.factory.annotation.Autowired;
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
   
    // -------------------- GET ALL BY TENANT --------------------
    @GetMapping("/tenant/{tenantId}")
    public List<PeelingReport> getReportsByTenant(@PathVariable Long tenantId) {
        return peelingReportService.getReportsByTenant(tenantId);
    }

    // -------------------- GET BY EMPLOYEE --------------------
    @GetMapping("/tenant/{tenantId}/employee/{employeeId}")
    public List<PeelingReport> getReportsByEmployee(
            @PathVariable Long tenantId,
            @PathVariable Long employeeId
    ) {
        return peelingReportService.getReportsByTenantAndEmployee(tenantId, employeeId);
    }

    // -------------------- GET BY DATE --------------------
    @GetMapping("/tenant/{tenantId}/date/{date}")
    public List<PeelingReport> getReportsByDate(
            @PathVariable Long tenantId,
            @PathVariable String date
    ) {
        return peelingReportService.getReportsByDate(
                tenantId,
                LocalDate.parse(date)
        );
    }

    // -------------------- GET PENDING REPORTS --------------------
    @GetMapping("/tenant/{tenantId}/pending")
    public List<PeelingReport> getPendingReports(@PathVariable Long tenantId) {
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
}
