package com.fictilecore.crm.fictilecoreCRM.controller;

import com.fictilecore.crm.fictilecoreCRM.dto.BormaReportRequestDTO;
import com.fictilecore.crm.fictilecoreCRM.entity.BormaReport;
import com.fictilecore.crm.fictilecoreCRM.entity.Employee;
import com.fictilecore.crm.fictilecoreCRM.entity.Tenant;
import com.fictilecore.crm.fictilecoreCRM.mapper.BormaReportMapper;
import com.fictilecore.crm.fictilecoreCRM.repository.EmployeeRepository;
import com.fictilecore.crm.fictilecoreCRM.repository.TenantRepository;
import com.fictilecore.crm.fictilecoreCRM.service.BormaReportService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/borma-reports")
@CrossOrigin(origins = "*")
public class BormaReportController {

    @Autowired
    private TenantRepository tenantRepo;

    @Autowired
    private EmployeeRepository employeeRepo;

    @Autowired
    private BormaReportMapper mapper;

    @Autowired
    private BormaReportService service;

    // -------------------- CREATE REPORT --------------------
    @PostMapping("/tenant/{tenantId}/employee/{employeeId}")
    public BormaReport create(
            @PathVariable Long tenantId,
            @PathVariable Long employeeId,
            @RequestBody BormaReportRequestDTO dto
    ) {
        Tenant tenant = tenantRepo.findById(tenantId)
                .orElseThrow(() -> new RuntimeException("Tenant not found"));

        Employee employee = employeeRepo.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        BormaReport report = mapper.toEntity(dto, tenant, employee);
        return service.save(report);
    }

    // -------------------- GET ALL BY TENANT --------------------
    @GetMapping("/tenant/{tenantId}")
    public List<BormaReport> getAllByTenant(@PathVariable Long tenantId) {
        return service.getAll(tenantId);
    }
     
    // -------------------- GET BY EMPLOYEE --------------------
    @GetMapping("/tenant/{tenantId}/employee/{employeeId}")
    public List<BormaReport> getByEmployee(
            @PathVariable Long tenantId,
            @PathVariable Long employeeId
    ) {
        return service.getByTenantAndEmployee(tenantId, employeeId);
    }

    // -------------------- GET BY DATE --------------------
    @GetMapping("/tenant/{tenantId}/date/{date}")
    public List<BormaReport> getByDate(
            @PathVariable Long tenantId,
            @PathVariable String date
    ) {
        return service.getByDate(tenantId, LocalDate.parse(date));
    }

    // -------------------- GET PENDING REPORTS --------------------
    @GetMapping("/tenant/{tenantId}/pending")
    public List<BormaReport> getPendingReports(@PathVariable Long tenantId) {
        return service.getAllExceptCompletedReportsByTenant(tenantId);
    }

    // -------------------- PATCH REPORT (PARTIAL UPDATE) --------------------
    @PatchMapping("/{id}")
    public BormaReport patchReport(
            @PathVariable Long id,
            @RequestBody BormaReport updatedFields
    ) {
        return service.patchReport(id, id, updatedFields);
    }

    // -------------------- DELETE REPORT --------------------
    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        service.deleteById(id);
        return "Borma report deleted successfully";
    }
}
