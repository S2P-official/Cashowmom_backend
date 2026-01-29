package com.fictilecore.crm.fictilecoreCRM.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fictilecore.crm.fictilecoreCRM.dto.ShellingDaySummaryDTO;
import com.fictilecore.crm.fictilecoreCRM.dto.ShellingReportDTO;
import com.fictilecore.crm.fictilecoreCRM.entity.Employee;
import com.fictilecore.crm.fictilecoreCRM.entity.ShellingReport;
import com.fictilecore.crm.fictilecoreCRM.entity.Tenant;
import com.fictilecore.crm.fictilecoreCRM.mapper.MonthlyShellingResponse;
import com.fictilecore.crm.fictilecoreCRM.repository.EmployeeRepository;
import com.fictilecore.crm.fictilecoreCRM.repository.ShellingReportRepository;
import com.fictilecore.crm.fictilecoreCRM.repository.TenantRepository;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ShellingReportService {

    @Autowired
    private ShellingReportRepository shellingReportRepository;

    @Autowired
    private TenantRepository tenantRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private SubscriptionService subscriptionService;

    // ---------------- CREATE MULTIPLE REPORTS ----------------
    public List<ShellingReportDTO> createReport(
            Long tenantId,
            Long employeeId,
            List<ShellingReportDTO> dtos
    ) {
        subscriptionService.validateTenantSubscription(tenantId);

        Tenant tenant = tenantRepository.findById(tenantId)
                .orElseThrow(() -> new RuntimeException("Tenant not found: " + tenantId));

        final Employee employee = (employeeId == null)
                ? null
                : employeeRepository.findById(employeeId)
                    .orElseThrow(() -> new RuntimeException("Employee not found: " + employeeId));

        List<ShellingReport> reports = dtos.stream()
                .map(dto -> convertToEntity(dto, tenant, employee))
                .collect(Collectors.toList());

        return shellingReportRepository.saveAll(reports)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // ---------------- GET REPORTS BY TENANT ----------------
    public List<ShellingReportDTO> getReportsByTenant(Long tenantId) {
        subscriptionService.validateTenantSubscription(tenantId);

        return shellingReportRepository.findByTenant_Id(tenantId)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // ---------------- GET REPORTS BY DATE ----------------
    public List<ShellingReportDTO> getReportsByDate(Long tenantId, LocalDate date) {
        subscriptionService.validateTenantSubscription(tenantId);

        return shellingReportRepository.findByTenant_IdAndDate(tenantId, date)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // ---------------- GET REPORTS BY TENANT + EMPLOYEE ----------------
    public List<ShellingReportDTO> getReportsByTenantAndEmployee(
            Long tenantId,
            Long employeeId
    ) {
        subscriptionService.validateTenantSubscription(tenantId);

        return shellingReportRepository
                .findByTenant_IdAndEmployee_Id(tenantId, employeeId)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // ---------------- PATCH / UPDATE ----------------
    public ShellingReport patchReport(
            Long tenantId,
            Long id,
            ShellingReport updatedFields
    ) {
        subscriptionService.validateTenantSubscription(tenantId);

        ShellingReport existing = shellingReportRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Shelling report not found: " + id));

        if (updatedFields.getStatus() != null)
            existing.setStatus(updatedFields.getStatus());
        if (updatedFields.getNoOfBags() != null)
            existing.setNoOfBags(updatedFields.getNoOfBags());
        if (updatedFields.getTotal() != null)
            existing.setTotal(updatedFields.getTotal());

        return shellingReportRepository.save(existing);
    }

    // ---------------- DELETE ----------------
    public void deleteReport(Long tenantId, Long id) {
        subscriptionService.validateTenantSubscription(tenantId);

        ShellingReport existing = shellingReportRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Shelling report not found: " + id));

        shellingReportRepository.delete(existing);
    }

    // ---------------- GET ALL EXCEPT COMPLETED ----------------
    public List<ShellingReport> getAllExceptCompletedReportsByTenant(Long tenantId) {
        subscriptionService.validateTenantSubscription(tenantId);
        return shellingReportRepository.findAllExceptCompletedByTenant(tenantId);
    }

    // ---------------- SUMMARY BETWEEN DATES ----------------
    public List<ShellingDaySummaryDTO> getSummaryBetweenDates(
            Long tenantId,
            LocalDate from,
            LocalDate to
    ) {
        subscriptionService.validateTenantSubscription(tenantId);

        List<ShellingReport> reports =
                shellingReportRepository.findByTenant_IdAndDateBetween(tenantId, from, to);

        Map<LocalDate, List<ShellingReport>> grouped =
                reports.stream().collect(Collectors.groupingBy(ShellingReport::getDate));

        List<ShellingDaySummaryDTO> result = new ArrayList<>();

        for (Map.Entry<LocalDate, List<ShellingReport>> entry : grouped.entrySet()) {

            int totalWholes = entry.getValue().stream()
                    .mapToInt(r -> r.getWholes() == null ? 0 : r.getWholes())
                    .sum();

            int totalBrokens = entry.getValue().stream()
                    .mapToInt(r -> r.getBroken() == null ? 0 : r.getBroken())
                    .sum();

            ShellingDaySummaryDTO dto = new ShellingDaySummaryDTO();
            dto.setDate(entry.getKey().toString());
            dto.setTotalWholes(totalWholes);
            dto.setTotalBrokens(totalBrokens);

            result.add(dto);
        }

        return result;
    }

    // ---------------- MONTHLY REPORT ----------------
    public MonthlyShellingResponse getMonthlyReport(
            Long tenantId,
            int year,
            int month
    ) {
        subscriptionService.validateTenantSubscription(tenantId);

        LocalDate start = LocalDate.of(year, month, 1);
        LocalDate end = start.withDayOfMonth(start.lengthOfMonth());

        Map<String, List<ShellingReportDTO>> grouped =
                shellingReportRepository
                        .findByTenant_IdAndDateBetween(tenantId, start, end)
                        .stream()
                        .map(this::convertToDTO)
                        .collect(Collectors.groupingBy(
                                dto -> dto.getDate().toString(),
                                LinkedHashMap::new,
                                Collectors.toList()
                        ));

        return new MonthlyShellingResponse(year, month, grouped);
    }

    // ---------------- MAPPERS ----------------
    private ShellingReport convertToEntity(
            ShellingReportDTO dto,
            Tenant tenant,
            Employee employee
    ) {
        ShellingReport r = new ShellingReport();
        r.setDate(dto.getDate());
        r.setLotMark(dto.getLotMark());
        r.setOrigin(dto.getOrigin());
        r.setPerBagWeight(dto.getPerBagWeight());
        r.setSizeRange(dto.getSizeRange());
        r.setNoOfBags(dto.getNoOfBags());
        r.setProductionQty(dto.getProductionQty());
        r.setTotalProductionMts(dto.getTotalProductionMts());
        r.setPercentage(dto.getPercentage());
        r.setCountPerKg(dto.getCountPerKg());
        r.setStatus(dto.getStatus());
        r.setCookingTime(dto.getCookingTime());
        r.setDryRcnMoisture(dto.getDryRcnMoisture());
        r.setRoasterName(dto.getRoasterName());
        r.setTempForVnMachine(dto.getTempForVnMachine());
        r.setRoastingDuration(dto.getRoastingDuration());
        r.setSoackingMoisture(dto.getSoackingMoisture());
        r.setMoistureAfterRoasting(dto.getMoistureAfterRoasting());
        r.setTotalRoasted(dto.getTotalRoasted());
        r.setCuttingLine(dto.getCuttingLine());
        r.setWholes(dto.getWholes());
        r.setBroken(dto.getBroken());
        r.setRejection(dto.getRejection());
        r.setUncut(dto.getUncut());
        r.setPartly(dto.getPartly());
        r.setTotal(dto.getTotal());
        r.setTenant(tenant);
        r.setEmployee(employee);
        return r;
    }

    private ShellingReportDTO convertToDTO(ShellingReport r) {
        ShellingReportDTO dto = new ShellingReportDTO();
        dto.setId(r.getId());
        dto.setDate(r.getDate());
        dto.setLotMark(r.getLotMark());
        dto.setOrigin(r.getOrigin());
        dto.setPerBagWeight(r.getPerBagWeight());
        dto.setSizeRange(r.getSizeRange());
        dto.setNoOfBags(r.getNoOfBags());
        dto.setProductionQty(r.getProductionQty());
        dto.setTotalProductionMts(r.getTotalProductionMts());
        dto.setPercentage(r.getPercentage());
        dto.setCountPerKg(r.getCountPerKg());
        dto.setStatus(r.getStatus());
        dto.setCookingTime(r.getCookingTime());
        dto.setDryRcnMoisture(r.getDryRcnMoisture());
        dto.setRoasterName(r.getRoasterName());
        dto.setTempForVnMachine(r.getTempForVnMachine());
        dto.setRoastingDuration(r.getRoastingDuration());
        dto.setSoackingMoisture(r.getSoackingMoisture());
        dto.setMoistureAfterRoasting(r.getMoistureAfterRoasting());
        dto.setTotalRoasted(r.getTotalRoasted());
        dto.setCuttingLine(r.getCuttingLine());
        dto.setWholes(r.getWholes());
        dto.setBroken(r.getBroken());
        dto.setRejection(r.getRejection());
        dto.setUncut(r.getUncut());
        dto.setPartly(r.getPartly());
        dto.setTotal(r.getTotal());
        return dto;
    }
}
