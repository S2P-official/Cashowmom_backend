package com.fictilecore.crm.fictilecoreCRM.controller;

import com.fictilecore.crm.fictilecoreCRM.entity.GradingReport;
import com.fictilecore.crm.fictilecoreCRM.service.GradingReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/grading-reports")
@CrossOrigin(origins = "*")
public class GradingReportController {

    @Autowired
    private GradingReportService gradingReportService;

    // ---------------- CREATE ----------------
    @PostMapping("/tenant/{tenantId}/employee/{employeeId}/borma/{bormaReportId}")
    public List<GradingReport> createReport(
            @PathVariable Long tenantId,
            @PathVariable Long employeeId,
            @PathVariable Long bormaReportId,
            @RequestBody List<GradingReport> reports
    ) {
        return gradingReportService.createReport(tenantId, employeeId, bormaReportId, reports);
    }

    // ---------------- GET ALL REPORTS FOR TENANT ----------------
    @GetMapping("/tenant/{tenantId}")
    public List<GradingReport> getReportsByTenant(@PathVariable Long tenantId) {
        return gradingReportService.getReportsByTenant(tenantId);
    }

    // ---------------- GET REPORTS BY DATE ----------------
    @GetMapping("/tenant/{tenantId}/date/{date}")
    public List<GradingReport> getReportsByDate(
            @PathVariable Long tenantId,
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date
    ) {
        return gradingReportService.getReportsByDate(tenantId, date);
    }

    // ---------------- GET REPORTS BY GRADE ----------------
    @GetMapping("/tenant/{tenantId}/grade/{grade}")
    public List<GradingReport> getReportsByGrade(
            @PathVariable Long tenantId,
            @PathVariable String grade
    ) {
        return gradingReportService.getReportsByGrade(tenantId, grade);
    }

    // ---------------- DELETE REPORT ----------------
    @DeleteMapping("/tenant/{tenantId}/report/{id}")
    public String deleteReport(
            @PathVariable Long tenantId,
            @PathVariable Long id
    ) {
        // Optional: add tenant check in service if needed
        gradingReportService.deleteReport(id, id);
        return "Grading report deleted successfully";
    }
}
