package com.fictilecore.crm.fictilecoreCRM.controller;

import com.fictilecore.crm.fictilecoreCRM.dto.RoastingReportResponse;
import com.fictilecore.crm.fictilecoreCRM.dto.ShellingDaySummaryDTO;
import com.fictilecore.crm.fictilecoreCRM.dto.ShellingReportDTO;
import com.fictilecore.crm.fictilecoreCRM.dto.ShellingReportResponseDTO;
import com.fictilecore.crm.fictilecoreCRM.entity.ShellingReport;
import com.fictilecore.crm.fictilecoreCRM.mapper.MonthlyShellingResponse;
import com.fictilecore.crm.fictilecoreCRM.service.ShellingReportService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/shelling-reports")
@CrossOrigin(origins = "*")
public class ShellingReportController {

    @Autowired
    private ShellingReportService shellingReportService;

    // -------------------- CREATE SINGLE REPORT --------------------
    @PostMapping("/tenant/{tenantId}/employee/{employeeId}")
    public ShellingReportDTO createReport(
            @PathVariable Long tenantId,
            @PathVariable(required = false) Long employeeId,
            @RequestBody ShellingReportDTO reportDTO
    ) {
        return shellingReportService
                .createReport(tenantId, employeeId, List.of(reportDTO))
                .get(0);
    }

    // -------------------- GET ALL REPORTS FOR TENANT --------------------
    @GetMapping("/tenant/{tenantId}")
    public List<ShellingReportDTO> getReportsByTenant(@PathVariable Long tenantId) {
        return shellingReportService.getReportsByTenant(tenantId);
    }

    // -------------------- GET REPORTS FOR A SPECIFIC EMPLOYEE --------------------
    @GetMapping("/tenant/{tenantId}/employee/{employeeId}")
    public List<ShellingReportDTO> getReportsByEmployee(
            @PathVariable Long tenantId,
            @PathVariable Long employeeId
    ) {
        return shellingReportService.getReportsByTenantAndEmployee(tenantId, employeeId);
    }

    // -------------------- GET REPORTS BY DATE --------------------
    @GetMapping("/tenant/{tenantId}/date/{date}")
    public List<ShellingReportDTO> getReportsByDate(
            @PathVariable Long tenantId,
            @PathVariable String date
    ) {
        return shellingReportService.getReportsByDate(
                tenantId,
                LocalDate.parse(date)
        );
    }

    // -------------------- GET MONTHLY SUMMARY --------------------
    @GetMapping("/tenant/{tenantId}/month/{year}/{month}")
    public MonthlyShellingResponse getMonthlyReport(
            @PathVariable Long tenantId,
            @PathVariable int year,
            @PathVariable int month
    ) {
        return shellingReportService.getMonthlyReport(tenantId, year, month);
    }

    // -------------------- GET SUMMARY BETWEEN DATES --------------------
    @GetMapping("/tenant/{tenantId}/summary")
    public List<ShellingDaySummaryDTO> getSummaryBetweenDates(
            @PathVariable Long tenantId,
            @RequestParam String from,
            @RequestParam String to
    ) {
        return shellingReportService.getSummaryBetweenDates(
                tenantId,
                LocalDate.parse(from),
                LocalDate.parse(to)
        );
    }

    // -------------------- GET PENDING REPORTS (status != Completed) --------------------
    @GetMapping("/tenant/{tenantId}/pending")
    public List<ShellingReport> getAllPendingShellingReports(
            @PathVariable Long tenantId
    ) {
        return shellingReportService.getAllExceptCompletedReportsByTenant(tenantId);
    }

    // -------------------- PATCH REPORT (PARTIAL UPDATE) --------------------
    @PatchMapping("/tenant/{tenantId}/report/{id}")
    public ShellingReport patchReport(
            @PathVariable Long tenantId,
            @PathVariable Long id,
            @RequestBody ShellingReport updatedFields
    ) {
        return shellingReportService.patchReport(tenantId, id, updatedFields);
    }

    // -------------------- DELETE REPORT --------------------
    @DeleteMapping("/tenant/{tenantId}/{id}")
    public String deleteReport(
            @PathVariable Long tenantId,
            @PathVariable Long id
    ) {
        shellingReportService.deleteReport(tenantId, id);
        return "Shelling report deleted successfully";
    }


    @GetMapping("/ViewEmplyeeUpdates")
public ResponseEntity<List<ShellingReportResponseDTO>> getRoastingReports(
        @RequestParam Long tenantId,
        @RequestParam Long employeeId,
        @RequestParam(required = false)
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        LocalDate date
) {
    List<ShellingReportResponseDTO> reports =
            shellingReportService.getReportsByTenantEmployeeAndDate(
                    tenantId, employeeId, date
            );

    return ResponseEntity.ok(reports);
}

}
