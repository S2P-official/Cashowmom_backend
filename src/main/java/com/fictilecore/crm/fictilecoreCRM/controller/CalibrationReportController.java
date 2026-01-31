package com.fictilecore.crm.fictilecoreCRM.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.fictilecore.crm.fictilecoreCRM.dto.CalibrationReportDTO;
import com.fictilecore.crm.fictilecoreCRM.dto.CalibrationReportDaySummaryResponse;
import com.fictilecore.crm.fictilecoreCRM.dto.CalibrationReportResponse;
import com.fictilecore.crm.fictilecoreCRM.entity.CalibrationReport;
import com.fictilecore.crm.fictilecoreCRM.service.CalibrationReportService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/calibration-reports")
@CrossOrigin(origins = "*")
public class CalibrationReportController {

    @Autowired
    private CalibrationReportService calibrationReportService;

    // ✅ Create report
    @PostMapping("/tenant/{tenantId}/employee/{employeeId}")
    public List<CalibrationReport> createReport(
            @PathVariable Long tenantId,
            @PathVariable(required = false) Long employeeId,
            @RequestBody List<CalibrationReport> report
    ) {
        return calibrationReportService.createReports(tenantId, employeeId, report);
    }

    // ✅ Get all reports for a tenant
    @GetMapping("/tenant/{tenantId}")
    public List<CalibrationReport> getReportsByTenant(@PathVariable Long tenantId) {
        return calibrationReportService.getReportsByTenant(tenantId);
    }

    // ✅ Get only reports for a specific employee (DTO response)
    @GetMapping("/tenant/{tenantId}/employee/{employeeId}")
    public List<CalibrationReportDTO> getReportsByEmployee(
            @PathVariable Long tenantId,
            @PathVariable Long employeeId
    ) {
        return calibrationReportService.getReportsByTenantAndEmployee(tenantId, employeeId);
    }

    // ✅ Get reports by date
@GetMapping("/tenant/{tenantId}/date/{date}")
public CalibrationReportDaySummaryResponse getReportsByTenantAndDate(
        @PathVariable Long tenantId,
        @PathVariable String date
) {
    return calibrationReportService
            .getReportsByTenantAndDate(tenantId, LocalDate.parse(date));
}


    // ✅ Delete report
    @DeleteMapping("/{id}")
    public String deleteReport(@PathVariable Long id) {
        calibrationReportService.deleteReport(id);
        return "Calibration report deleted successfully";
    }



    // ✅ Update report by ID
// ✅ Partial update report by ID
@PatchMapping("/{id}")
public CalibrationReport patchReport(
        @PathVariable Long id,
        @RequestBody CalibrationReport updatedFields
) {
    return calibrationReportService.patchReport(id, updatedFields);
}



// ✅ Get reports for a tenant where status is not "Completed"
// ✅ Get all reports for a tenant except those with status 'Completed'
@GetMapping("/tenant/{tenantId}/pending")
public List<CalibrationReport> getAllExceptCompletedReports(@PathVariable Long tenantId) {
    return calibrationReportService.getAllExceptCompletedReportsByTenant(tenantId);
}

@GetMapping
public ResponseEntity<List<CalibrationReportResponse>> getReports(
        @RequestParam Long tenantId,
        @RequestParam Long employeeId,
        @RequestParam(required = false)
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        LocalDate date
) {
    List<CalibrationReportResponse> reports = calibrationReportService
            .getReportsByTenantEmployeeAndDate(tenantId, employeeId, date);
    return ResponseEntity.ok(reports);
}


}
