package com.fictilecore.crm.fictilecoreCRM.service;
import com.fictilecore.crm.fictilecoreCRM.dto.BormaReportResponse;
import com.fictilecore.crm.fictilecoreCRM.entity.BormaReport;
import com.fictilecore.crm.fictilecoreCRM.entity.Employee;
import com.fictilecore.crm.fictilecoreCRM.entity.Tenant;
import com.fictilecore.crm.fictilecoreCRM.repository.BormaReportRepository;
import com.fictilecore.crm.fictilecoreCRM.repository.EmployeeRepository;
import com.fictilecore.crm.fictilecoreCRM.repository.TenantRepository;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class BormaReportService {

    private final TenantRepository tenantRepository;
    private final EmployeeRepository employeeRepository;
    private final BormaReportRepository bormaReportRepository;

      public BormaReport createReport(
            Long tenantId,
            Long employeeId,
            BormaReport report
    ) {
        Tenant tenant = tenantRepository.findById(tenantId)
                .orElseThrow(() ->
                        new RuntimeException("Tenant not found with ID: " + tenantId));

        report.setTenant(tenant);

        if (employeeId != null) {
            Employee employee = employeeRepository.findById(employeeId)
                    .orElseThrow(() ->
                            new RuntimeException("Employee not found with ID: " + employeeId));
            report.setEmployee(employee);
        }

        return bormaReportRepository.save(report);
    }


    
  public BormaReport patchReport(
            Long tenantId,
            Long reportId,
            BormaReport updatedFields
    ) {
        BormaReport existing =
                bormaReportRepository.findByIdAndTenant_Id(reportId, tenantId)
                        .orElseThrow(() ->
                                new RuntimeException("Report not found with ID: " + reportId));

        if (updatedFields.getStatus() != null)
            existing.setStatus(updatedFields.getStatus());

        return bormaReportRepository.save(existing);
    }


        // ---------------- OTHER ----------------
        public List<BormaReport> getAllExceptCompletedReportsByTenant(
                        Long tenantId) {
                return bormaReportRepository.findAllExceptCompletedByTenant(tenantId);
        }



    public List<BormaReportResponse> getReportsByTenantEmployeeAndDate(
                        Long tenantId,
                        Long employeeId,
                        LocalDate date) {
                // Use the fixed repository query
                List<BormaReport> reports = bormaReportRepository
                                .findReportsByTenantEmployeeAndDate(tenantId, employeeId, date);

                // Convert to DTO
                return reports.stream()
                                .map(BormaReportResponse::fromEntity)
                                .toList();
        }



      // ---------------- DELETE ----------------
        public void deleteReport(Long tenantId, Long reportId) {
                BormaReport existing = bormaReportRepository.findByIdAndTenant_Id(reportId, tenantId)
                                .orElseThrow(() -> new RuntimeException("Report not found"));

                bormaReportRepository.delete(existing);
        }



}
