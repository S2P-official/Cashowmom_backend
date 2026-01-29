package com.fictilecore.crm.fictilecoreCRM.service;

import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private CalibrationReportRepository calibrationReportRepository;

    @Autowired
    private TenantRepository tenantRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    // ✅ Create new calibration reports
    public List<CalibrationReport> createReports(Long tenantId, Long employeeId, List<CalibrationReport> reports) {
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

    // ✅ Get all reports for a tenant
    public List<CalibrationReport> getReportsByTenant(Long tenantId) {
        return calibrationReportRepository.findByTenant_Id(tenantId);
    }

    // ✅ Get reports for a specific employee (returns DTO)
    public List<CalibrationReportDTO> getReportsByTenantAndEmployee(Long tenantId, Long employeeId) {

        List<CalibrationReport> reports =
                calibrationReportRepository.findByTenant_IdAndEmployee_Id(tenantId, employeeId);

        return reports.stream()
                .map(this::toDTO)  // using mapper inside same class
                .collect(Collectors.toList());
    }

    // ✅ Get reports by date
public CalibrationReportDaySummaryResponse getReportsByTenantAndDate(
        Long tenantId,
        LocalDate date
) {

    List<CalibrationReport> reports =
            calibrationReportRepository.findByTenant_IdAndDate(tenantId, date);

    // 1️⃣ Row-level DTOs
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

    // 2️⃣ Total production quantity (day)
    double totalProductionQty = reports.stream()
            .mapToDouble(r -> r.getProductionQty() != null ? r.getProductionQty() : 0)
            .sum();

    // 3️⃣ Size-wise production quantity
    Map<String, Double> sizeWiseProductionQty =
            reports.stream()
                    .collect(Collectors.groupingBy(
                            CalibrationReport::getSizeRange,
                            Collectors.summingDouble(
                                    r -> r.getProductionQty() != null ? r.getProductionQty() : 0
                            )
                    ));

    // 4️⃣ Wrap everything into summary DTO
    return new CalibrationReportDaySummaryResponse(
            date,
            totalProductionQty,
            sizeWiseProductionQty,
            reportDtos
    );
}



    // ✅ Delete report
    public void deleteReport(Long id) {
        calibrationReportRepository.deleteById(id);
    }

    // -----------------------------------------------------------
    // 🔥 PRIVATE MAPPER METHOD INSIDE SERVICE (NO EXTRA FILE)
    // -----------------------------------------------------------
    private CalibrationReportDTO toDTO(CalibrationReport report) {
        if (report == null) return null;

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

    








public CalibrationReport patchReport(Long id, CalibrationReport updatedFields) {
    CalibrationReport existing = calibrationReportRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Report not found with id: " + id));

    // Update only the fields that are not null
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


public List<CalibrationReport> getAllExceptCompletedReportsByTenant(Long tenantId) {
    return calibrationReportRepository.findAllExceptCompletedByTenant(tenantId);
}


}
