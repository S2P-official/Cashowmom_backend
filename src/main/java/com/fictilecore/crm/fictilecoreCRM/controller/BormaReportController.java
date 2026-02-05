package com.fictilecore.crm.fictilecoreCRM.controller;


import com.fictilecore.crm.fictilecoreCRM.dto.BormaReportResponse;
import com.fictilecore.crm.fictilecoreCRM.entity.BormaReport;
import com.fictilecore.crm.fictilecoreCRM.service.BormaReportService;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/borma-reports")
@RequiredArgsConstructor
@CrossOrigin(origins = "*") // ✅ Allow frontend/mobile access
public class BormaReportController {

        @Autowired
    private final BormaReportService bormaReportService;

        @PostMapping("/tenant/{tenantId}/employee/{employeeId}")
    public BormaReport createReport(
            @PathVariable Long tenantId,
            @PathVariable(required = false) Long employeeId, 
            @RequestBody BormaReport report
    ) {
        return bormaReportService.createReport(tenantId, employeeId, report);
    }

        // -------------------- PATCH REPORT (Partial Update) --------------------
    @PatchMapping("/tenant/{tenantId}/report/{reportId}")
    public BormaReport patchReport(
            @PathVariable Long tenantId,
            @PathVariable Long reportId,
            @RequestBody BormaReport updatedFields
    ) {
        return bormaReportService.patchReport(tenantId, reportId, updatedFields);
    }




        // -------------------- GET PENDING REPORTS (status != Completed) --------------------
    @GetMapping("/tenant/{tenantId}/pending")
    public List<BormaReport> getAllPendingReports(@PathVariable Long tenantId) {
        return bormaReportService.getAllExceptCompletedReportsByTenant(tenantId);
    }





    @GetMapping("/ViewEmplyeeUpdates")
public ResponseEntity<List<BormaReportResponse>> getRoastingReports(
        @RequestParam Long tenantId,
        @RequestParam Long employeeId,
        @RequestParam(required = false)
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        LocalDate date
) {
    List<BormaReportResponse> reports =
            bormaReportService.getReportsByTenantEmployeeAndDate(
                    tenantId, employeeId, date
            );

    return ResponseEntity.ok(reports);
}







    // -------------------- DELETE REPORT --------------------
    @DeleteMapping("/tenant/{tenantId}/report/{reportId}")
    public String deleteReport(
            @PathVariable Long tenantId,
            @PathVariable Long reportId
    ) {
        bormaReportService.deleteReport(tenantId, reportId);
        return "Roasting report deleted successfully";
    }


}

