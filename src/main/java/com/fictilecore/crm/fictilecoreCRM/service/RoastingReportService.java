package com.fictilecore.crm.fictilecoreCRM.service;

import com.fictilecore.crm.fictilecoreCRM.dto.RoastingReportDTO;
import com.fictilecore.crm.fictilecoreCRM.dto.RoastingReportDaySummaryResponse;
import com.fictilecore.crm.fictilecoreCRM.dto.RoastingReportResponse;
import com.fictilecore.crm.fictilecoreCRM.entity.Employee;
import com.fictilecore.crm.fictilecoreCRM.entity.RoastingReport;
import com.fictilecore.crm.fictilecoreCRM.entity.Tenant;
import com.fictilecore.crm.fictilecoreCRM.repository.EmployeeRepository;
import com.fictilecore.crm.fictilecoreCRM.repository.RoastingRepository;
import com.fictilecore.crm.fictilecoreCRM.repository.TenantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class RoastingReportService {

    @Autowired
    private RoastingRepository roastingRepository;

    @Autowired
    private TenantRepository tenantRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    // ---------------- CREATE ----------------
    public RoastingReport createReport(
            Long tenantId,
            Long employeeId,
            RoastingReport report
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

        return roastingRepository.save(report);
    }

    // ---------------- GET ----------------
    public List<RoastingReport> getReportsByTenant(Long tenantId) {
        return roastingRepository.findByTenant_Id(tenantId);
    }

    public List<RoastingReportDTO> getReportsByTenantAndEmployee(
            Long tenantId,
            Long employeeId
    ) {
        return roastingRepository
                .findByTenant_IdAndEmployee_Id(tenantId, employeeId)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public RoastingReportDaySummaryResponse getReportsByDate(
            Long tenantId,
            LocalDate date
    ) {
        List<RoastingReport> reports =
                roastingRepository.findByTenant_IdAndDate(tenantId, date);

        // Row-level DTOs
        List<RoastingReportResponse> reportDtos = reports.stream()
                .map(r -> new RoastingReportResponse(
                        r.getId(),
                        r.getDate(),
                        r.getLotMark(),
                        r.getOrigin(),
                        r.getPerBagWeight(),
                        r.getSizeRange(),
                        r.getNoOfBags(),
                        r.getProductionQty(),
                        r.getTotalProductionMts(),
                        r.getPercentage(),
                        r.getCountPerKg(),
                        r.getStatus(),
                        r.getCookingTime(),
                        r.getDryRcnMoisture(),
                        r.getRoasterName(),
                        r.getTempForVnMachine(),
                        r.getRoastingDuration(),
                        r.getSoackingMoisture(),
                        r.getMoistureAfterRoasting(),
                        r.getTotalRoasted(),
                        r.getCuttingLine(),
                        r.getEmployee() != null
                                ? r.getEmployee().getEmployee_name()
                                : null
                ))
                .toList();

        // Day total
        double totalRoastedSum = reports.stream()
                .mapToDouble(r -> parseTotalRoasted(r.getTotalRoasted()))
                .sum();

        // Cutting-line-wise total
        Map<String, Double> cuttingLineWiseTotalRoasted =
                reports.stream()
                        .collect(Collectors.groupingBy(
                                RoastingReport::getCuttingLine,
                                Collectors.summingDouble(
                                        r -> parseTotalRoasted(r.getTotalRoasted())
                                )
                        ));

        return new RoastingReportDaySummaryResponse(
                date,
                totalRoastedSum,
                cuttingLineWiseTotalRoasted,
                reportDtos
        );
    }

    // ---------------- PATCH / UPDATE ----------------
    public RoastingReport patchReport(
            Long tenantId,
            Long reportId,
            RoastingReport updatedFields
    ) {
        RoastingReport existing =
                roastingRepository.findByIdAndTenant_Id(reportId, tenantId)
                        .orElseThrow(() ->
                                new RuntimeException("Report not found with ID: " + reportId));

        if (updatedFields.getStatus() != null)
            existing.setStatus(updatedFields.getStatus());

        if (updatedFields.getNoOfBags() != null)
            existing.setNoOfBags(updatedFields.getNoOfBags());

        if (updatedFields.getProductionQty() != null)
            existing.setProductionQty(updatedFields.getProductionQty());

        if (updatedFields.getTotalRoasted() != null)
            existing.setTotalRoasted(updatedFields.getTotalRoasted());

        return roastingRepository.save(existing);
    }

    // ---------------- DELETE ----------------
    public void deleteReport(Long tenantId, Long reportId) {
        RoastingReport existing =
                roastingRepository.findByIdAndTenant_Id(reportId, tenantId)
                        .orElseThrow(() ->
                                new RuntimeException("Report not found"));

        roastingRepository.delete(existing);
    }

    public void deleteReportById(Long reportId) {
        roastingRepository.deleteById(reportId);
    }

    // ---------------- OTHER ----------------
    public List<RoastingReport> getAllExceptCompletedReportsByTenant(
            Long tenantId
    ) {
        return roastingRepository.findAllExceptCompletedByTenant(tenantId);
    }

    // ---------------- UTILS ----------------
    private double parseTotalRoasted(String value) {
        if (value == null || value.isBlank()) return 0.0;
        return Double.parseDouble(value);
    }

    private RoastingReportDTO convertToDTO(RoastingReport report) {
        RoastingReportDTO dto = new RoastingReportDTO();
        dto.setId(report.getId());
        dto.setDate(report.getDate());
        dto.setLotMark(report.getLotMark());
        dto.setOrigin(report.getOrigin());
        dto.setPerBagWeight(report.getPerBagWeight());
        dto.setSizeRange(report.getSizeRange());
        dto.setNoOfBags(report.getNoOfBags());
        dto.setProductionQty(report.getProductionQty());
        dto.setTotalProductionMts(report.getTotalProductionMts());
        dto.setPercentage(report.getPercentage());
        dto.setCountPerKg(report.getCountPerKg());
        dto.setStatus(report.getStatus());
        dto.setCookingTime(report.getCookingTime());
        dto.setDryRcnMoisture(report.getDryRcnMoisture());
        dto.setRoasterName(report.getRoasterName());
        dto.setTempForVnMachine(report.getTempForVnMachine());
        dto.setRoastingDuration(report.getRoastingDuration());
        dto.setSoackingMoisture(report.getSoackingMoisture());
        dto.setMoistureAfterRoasting(report.getMoistureAfterRoasting());
        dto.setTotalRoasted(report.getTotalRoasted());
        dto.setCuttingLine(report.getCuttingLine());
        return dto;
    }

 public List<RoastingReportResponse> getReportsByTenantEmployeeAndDate(
        Long tenantId,
        Long employeeId,
        LocalDate date
) {
    // Use the fixed repository query
    List<RoastingReport> reports = roastingRepository
            .findReportsByTenantEmployeeAndDate(tenantId, employeeId, date);

    // Convert to DTO
    return reports.stream()
            .map(RoastingReportResponse::fromEntity)
            .toList();
}

}
