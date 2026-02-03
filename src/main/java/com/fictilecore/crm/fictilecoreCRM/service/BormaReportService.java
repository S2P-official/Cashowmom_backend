package com.fictilecore.crm.fictilecoreCRM.service;

import com.fictilecore.crm.fictilecoreCRM.dto.BormaReportDTO;
import com.fictilecore.crm.fictilecoreCRM.dto.BormaReportDaySummaryResponse;
import com.fictilecore.crm.fictilecoreCRM.dto.BormaReportResponse;

import com.fictilecore.crm.fictilecoreCRM.entity.BormaReport;

import com.fictilecore.crm.fictilecoreCRM.entity.Employee;
import com.fictilecore.crm.fictilecoreCRM.entity.Tenant;
import com.fictilecore.crm.fictilecoreCRM.repository.BormaReportRepository;
import com.fictilecore.crm.fictilecoreCRM.repository.EmployeeRepository;
import com.fictilecore.crm.fictilecoreCRM.repository.TenantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BormaReportService {

    private final SubscriptionService subscriptionService;
    private final TenantRepository tenantRepository;
    private final EmployeeRepository employeeRepository;
    private final BormaReportRepository bormaReportRepository;

    // ---------------- CREATE MULTIPLE REPORTS ----------------
    public List<BormaReport> createReport(
            Long tenantId,
            Long employeeId,
            List<BormaReport> reports
    ) {
        // Validate tenant subscription
        subscriptionService.validateTenantSubscription(tenantId);

        // Fetch tenant
        Tenant tenant = tenantRepository.findById(tenantId)
                .orElseThrow(() -> new RuntimeException("Tenant not found: " + tenantId));

        // Fetch employee (mandatory)
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found: " + employeeId));

        // Set tenantId and employee for each report
        for (BormaReport report : reports) {
            report.setTenant(tenant);
            report.setEmployee(employee);
        }

        // Save all reports
        return bormaReportRepository.saveAll(reports);
    }

    // Optionally, you can add a GET method
    public List<BormaReport> getReportsByTenant(Long tenantId) {
        return bormaReportRepository.findByTenant_Id(tenantId);
    }

   public List<BormaReportDTO> getReportsByTenantAndEmployee(
            Long tenantId,
            Long employeeId
    ) {
        List<BormaReport> reports =
                bormaReportRepository.findByTenant_IdAndEmployee_Id(tenantId, employeeId);

        return reports.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
   private BormaReportDTO toDTO(BormaReport report) {

    if (report == null) {
        return null;
    }

    BormaReportDTO dto = new BormaReportDTO();

    dto.setId(report.getId() != null ? report.getId().toString() : null);
    dto.setLotMark(report.getLotMark());
    dto.setOrigin(report.getOrigin());
    dto.setPerBagWeight(report.getPerBagWeight());
    dto.setSizeRange(report.getSizeRange());
    dto.setCountPerKg(report.getCountPerKg());
    dto.setStatus(report.getStatus());

    dto.setCookingTime(report.getCookingTime());
    dto.setRoasterName(report.getRoasterName());
    dto.setMoistureAfterRoasting(report.getMoistureAfterRoasting());
    dto.setCuttingLine(report.getCuttingLine());
    dto.setAfterBormaKernalMoisture(report.getAfterBormaKernelMoisture());

    dto.setWholes(report.getWholes());
    dto.setBroken(report.getBroken());
    dto.setRejection(report.getRejection());
    dto.setUncut(report.getUncut());
    dto.setPartly(report.getPartly());
    dto.setTotal(report.getTotal());

    dto.setBormaTimeDuration(report.getBormaTimeDuration());
    dto.setBormaTemperature(report.getBormaTemperature());

    dto.setAftrBormaWholes(report.getAftrBormaWholes());
    dto.setAftrBormaBrokens(report.getAftrBormaBrokens());

    dto.setShortWholes(report.getShortWholes());
    dto.setShortBrokens(report.getShortBrokens());

    return dto;
}

public BormaReportDaySummaryResponse getReportsByTenantAndDate(
        Long tenantId,
        LocalDate date
) {
    List<BormaReport> reports =
            bormaReportRepository.findByTenant_IdAndDate(tenantId, date);

    // 1️⃣ Row DTOs
    List<BormaReportResponse> reportDtos = reports.stream()
            .map(report -> new BormaReportResponse(
                    report.getId(),
                    report.getDate(),

                    report.getLotMark(),
                    report.getOrigin(),
                    report.getPerBagWeight(),
                    report.getSizeRange(),
                    report.getCountPerKg(),

                    report.getCookingTime(),
                    report.getRoasterName(),
                    report.getMoistureAfterRoasting(),
                    report.getCuttingLine(),
                    report.getAfterBormaKernelMoisture(),

                    report.getWholes(),
                    report.getBroken(),
                    report.getRejection(),
                    report.getUncut(),
                    report.getPartly(),
                    report.getTotal(),

                    report.getBormaTimeDuration(),
                    report.getBormaTemperature(),

                    report.getAftrBormaWholes(),
                    report.getAftrBormaBrokens(),
                    report.getShortWholes(),
                    report.getShortBrokens(),

                    report.getTotalWholes(),
                    report.getTotalShort(),

                    report.getCountPerKg(),
                    report.getEmployee() != null
                            ? report.getEmployee().getEmployee_name()
                            : null
            ))
            .toList();

    // 2️⃣ Total production (day)  ❌ you were using productionQty which does not exist in DTO
    double totalWholes = reports.stream()
            .mapToDouble(r -> r.getTotalWholes() != null ? r.getTotalWholes() : 0)
            .sum();

    // 3️⃣ Size-wise production (based on totalWholes)
    Map<String, Double> sizeWiseProductionQty =
            reports.stream()
                    .collect(Collectors.groupingBy(
                            BormaReport::getSizeRange,
                            Collectors.summingDouble(
                                    r -> r.getTotalWholes() != null ? r.getTotalWholes() : 0
                            )
                    ));

    return new BormaReportDaySummaryResponse(
            date,
            totalWholes,
            sizeWiseProductionQty,
            reportDtos
    );
}

  public BormaReport patchReport(Long id, BormaReport updatedFields) {
        BormaReport existing = bormaReportRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Report not found with id: " + id));

        if (updatedFields.getDate() != null) existing.setDate(updatedFields.getDate());
        if (updatedFields.getLotMark() != null) existing.setLotMark(updatedFields.getLotMark());
        if (updatedFields.getOrigin() != null) existing.setOrigin(updatedFields.getOrigin());
        if (updatedFields.getPerBagWeight() != null) existing.setPerBagWeight(updatedFields.getPerBagWeight());
        if (updatedFields.getSizeRange() != null) existing.setSizeRange(updatedFields.getSizeRange());
         if (updatedFields.getCountPerKg() != null) existing.setCountPerKg(updatedFields.getCountPerKg());
        if (updatedFields.getStatus() != null) existing.setStatus(updatedFields.getStatus());
        if (updatedFields.getTenant() != null) existing.setTenant(updatedFields.getTenant());
        if (updatedFields.getEmployee() != null) existing.setEmployee(updatedFields.getEmployee());

        return bormaReportRepository.save(existing);
    }

   public List<BormaReport> getAllExceptCompletedReportsByTenant(Long tenantId) {
        return bormaReportRepository.findAllExceptCompletedByTenant( tenantId);
    }

  public List<BormaReportResponse> getReportsByTenantEmployeeAndDate(
        Long tenantId,
        Long employeeId,
        LocalDate date
) {
    return bormaReportRepository.findReportsByTenantEmployeeAndDate(tenantId, employeeId, date);
}

}
