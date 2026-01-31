package com.fictilecore.crm.fictilecoreCRM.service;

import org.springframework.stereotype.Service;

import com.fictilecore.crm.fictilecoreCRM.dto.CalibrationReportDTO;
import com.fictilecore.crm.fictilecoreCRM.dto.CalibrationReportDaySummaryResponse;
import com.fictilecore.crm.fictilecoreCRM.dto.CalibrationReportResponse;
import com.fictilecore.crm.fictilecoreCRM.entity.CalibrationReport;
import com.fictilecore.crm.fictilecoreCRM.entity.Employee;
import com.fictilecore.crm.fictilecoreCRM.entity.Tenant;
import com.fictilecore.crm.fictilecoreCRM.repository.CalibrationReportRepository;
import com.fictilecore.crm.fictilecoreCRM.repository.EmployeeRepository;
import com.fictilecore.crm.fictilecoreCRM.repository.TenantRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CalibrationReportService {

    private final CalibrationReportRepository calibrationReportRepository;
    private final TenantRepository tenantRepository;
    private final EmployeeRepository employeeRepository;

    // ✅ SINGLE constructor injection (correct)
    public CalibrationReportService(
            CalibrationReportRepository calibrationReportRepository,
            TenantRepository tenantRepository,
            EmployeeRepository employeeRepository
    ) {
        this.calibrationReportRepository = calibrationReportRepository;
        this.tenantRepository = tenantRepository;
        this.employeeRepository = employeeRepository;
    }

    // -------------------------------------------------------
    // ✅ CREATE REPORTS
    // -------------------------------------------------------
    public List<CalibrationReport> createReports(
            Long tenantId,
            Long employeeId,
            List<CalibrationReport> reports
    ) {
        Tenant tenant = tenantRepository.findById(tenantId)
                .orElseThrow(() -> new RuntimeException("Tenant not found with ID: " + tenantId));

        Employee employee = null;
        if (employeeId != null) {
            employee = employeeRepository.findById(employeeId)
                    .orElseThrow(() -> new RuntimeException("Employee not found with ID: " + employeeId));
        }

        for (CalibrationReport report : reports) {
            report.setTenant(tenant);
            report.setEmployee(employee);
        }

        return calibrationReportRepository.saveAll(reports);
    }

    // -------------------------------------------------------
    // ✅ GET ALL REPORTS BY TENANT
    // -------------------------------------------------------
    public List<CalibrationReport> getReportsByTenant(Long tenantId) {
        return calibrationReportRepository.findByTenant_Id(tenantId);
        

    }

    // -------------------------------------------------------
    // ✅ GET REPORTS BY TENANT + EMPLOYEE (DTO)
    // -------------------------------------------------------
    public List<CalibrationReportDTO> getReportsByTenantAndEmployee(
            Long tenantId,
            Long employeeId
    ) {
        List<CalibrationReport> reports =
                calibrationReportRepository.findByTenant_IdAndEmployee_Id(tenantId, employeeId);

        return reports.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    // -------------------------------------------------------
    // ✅ GET REPORTS BY DATE (DAY SUMMARY)
    // -------------------------------------------------------
    public CalibrationReportDaySummaryResponse getReportsByTenantAndDate(
            Long tenantId,
            LocalDate date
    ) {
        List<CalibrationReport> reports =
                calibrationReportRepository.findByTenant_IdAndDate(tenantId, date);

        // 1️⃣ Row DTOs
        List<CalibrationReportResponse> reportDtos = reports.stream()
                .map(report -> new CalibrationReportResponse(
                        report.getId(),
                        report.getDate(),
                        report.getLotMark(),
                        report.getOrigin(),
                        report.getPerBagWeight(),
                        report.getSizeRange(),
                        report.getNoOfBags(),
                        report.getProductionQty(),
                        report.getCountPerKg(),
                        report.getEmployee() != null
                                ? report.getEmployee().getEmployee_name()
                                : null
                ))
                .toList();

        // 2️⃣ Total production (day)
        double totalProductionQty = reports.stream()
                .mapToDouble(r -> r.getProductionQty() != null ? r.getProductionQty() : 0)
                .sum();

        // 3️⃣ Size-wise production
        Map<String, Double> sizeWiseProductionQty =
                reports.stream()
                        .collect(Collectors.groupingBy(
                                CalibrationReport::getSizeRange,
                                Collectors.summingDouble(
                                        r -> r.getProductionQty() != null ? r.getProductionQty() : 0
                                )
                        ));

        return new CalibrationReportDaySummaryResponse(
                date,
                totalProductionQty,
                sizeWiseProductionQty,
                reportDtos
        );
    }

    // -------------------------------------------------------
    // ✅ PATCH UPDATE (PARTIAL UPDATE)
    // -------------------------------------------------------
    public CalibrationReport patchReport(Long id, CalibrationReport updatedFields) {
        CalibrationReport existing = calibrationReportRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Report not found with id: " + id));

        if (updatedFields.getDate() != null) existing.setDate(updatedFields.getDate());
        if (updatedFields.getLotMark() != null) existing.setLotMark(updatedFields.getLotMark());
        if (updatedFields.getOrigin() != null) existing.setOrigin(updatedFields.getOrigin());
        if (updatedFields.getPerBagWeight() != null) existing.setPerBagWeight(updatedFields.getPerBagWeight());
        if (updatedFields.getSizeRange() != null) existing.setSizeRange(updatedFields.getSizeRange());
        if (updatedFields.getNoOfBags() != null) existing.setNoOfBags(updatedFields.getNoOfBags());
        if (updatedFields.getProductionQty() != null) existing.setProductionQty(updatedFields.getProductionQty());
        if (updatedFields.getPercentage() != null) existing.setPercentage(updatedFields.getPercentage());
        if (updatedFields.getCountPerKg() != null) existing.setCountPerKg(updatedFields.getCountPerKg());
        if (updatedFields.getStatus() != null) existing.setStatus(updatedFields.getStatus());
        if (updatedFields.getTenant() != null) existing.setTenant(updatedFields.getTenant());
        if (updatedFields.getEmployee() != null) existing.setEmployee(updatedFields.getEmployee());

        return calibrationReportRepository.save(existing);
    }

    // -------------------------------------------------------
    // ✅ DELETE
    // -------------------------------------------------------
    public void deleteReport(Long id) {
        calibrationReportRepository.deleteById(id);
    }

    // -------------------------------------------------------
    // ✅ GET ALL EXCEPT COMPLETED
    // -------------------------------------------------------
    public List<CalibrationReport> getAllExceptCompletedReportsByTenant(Long tenantId) {
        return calibrationReportRepository.findAllExceptCompletedByTenant(tenantId);
    }

    // -------------------------------------------------------
    // ✅ ADVANCED FILTER (DTO QUERY)
    // -------------------------------------------------------
public List<CalibrationReportResponse> getReportsByTenantEmployeeAndDate(
        Long tenantId,
        Long employeeId,
        LocalDate date
) {
    return calibrationReportRepository.findReportsByTenantEmployeeAndDate(tenantId, employeeId, date);
}

    // -------------------------------------------------------
    // 🔥 PRIVATE MAPPER
    // -------------------------------------------------------
    private CalibrationReportDTO toDTO(CalibrationReport report) {
        return CalibrationReportDTO.builder()
                .id(report.getId())
                .date(report.getDate())
                .lotMark(report.getLotMark())
                .origin(report.getOrigin())
                .perBagWeight(report.getPerBagWeight())
                .sizeRange(report.getSizeRange())
                .noOfBags(report.getNoOfBags())
                .productionQty(report.getProductionQty())
                .percentage(report.getPercentage())
                .countPerKg(report.getCountPerKg())
                .tenantId(report.getTenant() != null ? report.getTenant().getId() : null)
                .employeeId(report.getEmployee() != null ? report.getEmployee().getId() : null)
                .build();
    }
}
