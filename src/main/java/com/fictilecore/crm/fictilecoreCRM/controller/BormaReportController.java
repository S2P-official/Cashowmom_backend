package com.fictilecore.crm.fictilecoreCRM.controller;

import com.fictilecore.crm.fictilecoreCRM.dto.BormaReportDTO;
import com.fictilecore.crm.fictilecoreCRM.dto.BormaReportDaySummaryResponse;
import com.fictilecore.crm.fictilecoreCRM.dto.BormaReportResponse;
import com.fictilecore.crm.fictilecoreCRM.entity.BormaReport;
import com.fictilecore.crm.fictilecoreCRM.service.BormaReportService;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/borma-reports")
@RequiredArgsConstructor
public class BormaReportController {

        @Autowired
    private final BormaReportService bormaReportService;


    

    // 🔹 POST – Save Borma Report
@PostMapping("/tenant/{tenantId}/employee/{employeeId}")
public BormaReport createReport(
        @PathVariable Long tenantId,
        @PathVariable(required = false) Long employeeId,
        @RequestBody BormaReport report
) {
    return bormaReportService
            .createReport(tenantId, employeeId, List.of(report)) // wrap in List
            .get(0); // return first (and only) report
}



      // 2️⃣ Get all BORMA reports for tenant
    @GetMapping("/tenant/{tenantId}")
    public List<BormaReport> getReportsByTenant(
            @PathVariable Long tenantId) {

        return bormaReportService.getReportsByTenant(tenantId);

    }



 // ✅ Get only reports for a specific employee (DTO response)
    @GetMapping("/tenant/{tenantId}/employee/{employeeId}")
    public List<BormaReportDTO> getReportsByEmployee(
            @PathVariable Long tenantId,
            @PathVariable Long employeeId
    ) {
        return bormaReportService.getReportsByTenantAndEmployee(tenantId, employeeId);
    }

    
    // ✅ Get reports by date
@GetMapping("/tenant/{tenantId}/date/{date}")
public BormaReportDaySummaryResponse getReportsByTenantAndDate(
        @PathVariable Long tenantId,
        @PathVariable String date
) {
    return bormaReportService
            .getReportsByTenantAndDate(tenantId, LocalDate.parse(date));
}




    // ✅ Update report by ID
// ✅ Partial update report by ID
@PatchMapping("/{id}")
public BormaReport patchReport(
        @PathVariable Long id,
        @RequestBody BormaReport updatedFields
) {
    return bormaReportService.patchReport(id, updatedFields);
}
    
    
// ✅ Get reports for a tenant where status is not "Completed"
// ✅ Get all reports for a tenant except those with status 'Completed'
@GetMapping("/tenant/{tenantId}/pending")
public List<BormaReport> getAllExceptCompletedReports(@PathVariable Long tenantId) {
    return bormaReportService.getAllExceptCompletedReportsByTenant(tenantId);
}



@GetMapping
public ResponseEntity<List<BormaReportResponse>> getReports(
        @RequestParam Long tenantId,
        @RequestParam Long employeeId,
        @RequestParam(required = false)
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        LocalDate date
) {
    List<BormaReportResponse> reports = bormaReportService
            .getReportsByTenantEmployeeAndDate(tenantId, employeeId, date);
    return ResponseEntity.ok(reports);
}

    // 3️⃣ Get BORMA reports by tenant + employee
    // @GetMapping("/tenant/{tenantId}/employee/{employeeId}")
    // public List<BormaReportDTO> getReportsByTenantAndEmployee(
    //         @PathVariable Long tenantId,
    //         @PathVariable Long employeeId) {

    //     return bormaReportService.getReportsByTenantAndEmployee(tenantId, employeeId);
    // }

    // // 4️⃣ Get BORMA reports by date
    // @GetMapping("/tenant/{tenantId}/date/{date}")
    // public List<BormaReportDTO> getReportsByDate(
    //         @PathVariable Long tenantId,
    //         @PathVariable String date) {

    //     return bormaReportService.getReportsByDate(tenantId, date);
    // }

    // // 5️⃣ Delete BORMA report
    // @DeleteMapping("/{id}")
    // public String deleteBormaReport(@PathVariable Long id) {
    //     bormaReportService.deleteBormaReport(id);
    //     return "Borma report deleted successfully";
    // }
}

