package com.fictilecore.crm.fictilecoreCRM.service;

import com.fictilecore.crm.fictilecoreCRM.entity.BormaReport;
import com.fictilecore.crm.fictilecoreCRM.entity.Employee;
import com.fictilecore.crm.fictilecoreCRM.entity.GradingReport;
import com.fictilecore.crm.fictilecoreCRM.entity.Tenant;
import com.fictilecore.crm.fictilecoreCRM.repository.BormaReportRepository;
import com.fictilecore.crm.fictilecoreCRM.repository.EmployeeRepository;
import com.fictilecore.crm.fictilecoreCRM.repository.GradingReportRepository;
import com.fictilecore.crm.fictilecoreCRM.repository.TenantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class GradingReportService {

    @Autowired
    private GradingReportRepository gradingReportRepository;

    @Autowired
    private TenantRepository tenantRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private BormaReportRepository bormaReportRepository;

    // ---------------- CREATE ----------------
    public List<GradingReport> createReport(Long tenantId, Long employeeId, Long bormaReportId, List<GradingReport> reports) {

        Tenant tenant = tenantRepository.findById(tenantId)
                .orElseThrow(() -> new RuntimeException("Tenant not found with ID: " + tenantId));

        Employee employee = null;
        if (employeeId != null) {
            employee = employeeRepository.findById(employeeId)
                    .orElseThrow(() -> new RuntimeException("Employee not found with ID: " + employeeId));
        }

        BormaReport bormaReport = null;
        if (bormaReportId != null) {
            bormaReport = bormaReportRepository.findById(bormaReportId)
                    .orElseThrow(() -> new RuntimeException("BormaReport not found with ID: " + bormaReportId));
        }

        // Set tenant, employee, and bormaReport for each grading report
        for (GradingReport report : reports) {
            report.setTenant(tenant);
            report.setEmployee(employee);
            report.setBormaReport(bormaReport);
        }

        return gradingReportRepository.saveAll(reports);
    }

    // ---------------- GET ----------------

    public List<GradingReport> getReportsByTenant(Long tenantId) {
        return gradingReportRepository.findByTenantId(tenantId);
    }

    public List<GradingReport> getReportsByDate(Long tenantId, LocalDate date) {
        return gradingReportRepository.findByTenantIdAndBormaReport_Date(tenantId, date);
    }

    public List<GradingReport> getReportsByGrade(Long tenantId, String grade) {
        return gradingReportRepository.findByTenantIdAndGrade(tenantId, grade);
    }

    // ---------------- UPDATE ----------------
    public GradingReport updateReport(Long tenantId, Long reportId, GradingReport updatedReport) {
        GradingReport existing = gradingReportRepository.findByIdAndTenant_Id(reportId, tenantId)
                .orElseThrow(() -> new RuntimeException("GradingReport not found"));

        // Update only fields that are not null
        if (updatedReport.getGrade() != null) existing.setGrade(updatedReport.getGrade());
        if (updatedReport.getWeightPeeled() != null) existing.setWeightPeeled(updatedReport.getWeightPeeled());
        if (updatedReport.getComments() != null) existing.setComments(updatedReport.getComments());

        return gradingReportRepository.save(existing);
    }

    // ---------------- DELETE ----------------
    public void deleteReport(Long tenantId, Long reportId) {
        GradingReport report = gradingReportRepository.findByIdAndTenant_Id(reportId, tenantId)
                .orElseThrow(() -> new RuntimeException("GradingReport not found"));
        gradingReportRepository.delete(report);
    }

    public void deleteReportById(Long reportId) {
        gradingReportRepository.deleteById(reportId);
    }
}
