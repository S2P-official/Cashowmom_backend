package com.fictilecore.crm.fictilecoreCRM.service;

import com.fictilecore.crm.fictilecoreCRM.dto.BormaDaySummaryResponse;
import com.fictilecore.crm.fictilecoreCRM.dto.BormaReportResponseDTO;
import com.fictilecore.crm.fictilecoreCRM.entity.BormaReport;
import com.fictilecore.crm.fictilecoreCRM.repository.BormaReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class BormaReportService {

    @Autowired
    private BormaReportRepository repo;

    @Autowired
    private SubscriptionService subscriptionService; // ✅ Validate tenant subscriptions

    // ---------------- CREATE ----------------
    public BormaReport save(BormaReport report) {
        Long tenantId = report.getTenant().getId();
        subscriptionService.validateTenantSubscription(tenantId);

        calculate(report);
        return repo.save(report);
    }

    // ---------------- UPDATE ----------------
    public BormaReport update(Long tenantId, Long id, BormaReport updated) {
        subscriptionService.validateTenantSubscription(tenantId);

        BormaReport existing = repo.findByIdAndTenant_Id(id, tenantId)
                .orElseThrow(() -> new RuntimeException("Report not found"));

        updated.setId(id);
        updated.setTenant(existing.getTenant());
        updated.setEmployee(existing.getEmployee());

        calculate(updated);
        return repo.save(updated);
    }

    // ---------------- GET ----------------
    public List<BormaReport> getAll(Long tenantId) {
        subscriptionService.validateTenantSubscription(tenantId);
        return repo.findByTenant_Id(tenantId);
    }

    public List<BormaReport> getByTenantAndEmployee(Long tenantId, Long employeeId) {
        subscriptionService.validateTenantSubscription(tenantId);
        return repo.findByTenant_IdAndEmployee_Id(tenantId, employeeId);
    }

    public List<BormaReport> getByDate(Long tenantId, LocalDate date) {
        subscriptionService.validateTenantSubscription(tenantId);
        return repo.findByTenant_IdAndDate(tenantId, date);
    }

    public List<BormaReport> getAllExceptCompletedReportsByTenant(Long tenantId) {
        subscriptionService.validateTenantSubscription(tenantId);
        return repo.findAllExceptCompletedByTenant(tenantId);
    }

    // ---------------- PATCH ----------------
    public BormaReport patchStatus(Long tenantId, Long id, String status) {
        subscriptionService.validateTenantSubscription(tenantId);

        BormaReport existing = repo.findByIdAndTenant_Id(id, tenantId)
                .orElseThrow(() -> new RuntimeException("Report not found"));

        existing.setStatus(status);
        return repo.save(existing);
    }

    public BormaReport patchReport(Long tenantId, Long id, BormaReport updatedFields) {
        subscriptionService.validateTenantSubscription(tenantId);

        BormaReport existing = repo.findByIdAndTenant_Id(id, tenantId)
                .orElseThrow(() -> new RuntimeException("Report not found"));

        // Update only fields that are not null
        if (updatedFields.getWholesReceived() != null) existing.setWholesReceived(updatedFields.getWholesReceived());
        if (updatedFields.getBrokensReceived() != null) existing.setBrokensReceived(updatedFields.getBrokensReceived());
        if (updatedFields.getWholesCountAfterBorma() != null) existing.setWholesCountAfterBorma(updatedFields.getWholesCountAfterBorma());
        if (updatedFields.getWholesShortCountAfterBorma() != null) existing.setWholesShortCountAfterBorma(updatedFields.getWholesShortCountAfterBorma());
        if (updatedFields.getBrokensCountAfterBorma() != null) existing.setBrokensCountAfterBorma(updatedFields.getBrokensCountAfterBorma());
        if (updatedFields.getBrokensShortCountAfterBorma() != null) existing.setBrokensShortCountAfterBorma(updatedFields.getBrokensShortCountAfterBorma());

        calculate(existing);
        return repo.save(existing);
    }

    // ---------------- DELETE ----------------
    public void delete(Long tenantId, Long id) {
        subscriptionService.validateTenantSubscription(tenantId);
        getById(tenantId, id);
        repo.deleteById(id);
    }

    public void deleteById(Long id) {
        repo.deleteById(id);
    }

    public BormaReport getById(Long tenantId, Long id) {
        subscriptionService.validateTenantSubscription(tenantId);
        return repo.findByIdAndTenant_Id(id, tenantId)
                .orElseThrow(() -> new RuntimeException("Report not found"));
    }

    // ---------------- DAY SUMMARY ----------------
    public BormaDaySummaryResponse getDaySummary(Long tenantId, LocalDate date) {
        subscriptionService.validateTenantSubscription(tenantId);

        List<BormaReport> reports = repo.findByTenant_IdAndDate(tenantId, date);

        double totalIssued = reports.stream().mapToDouble(r -> safe(r.getTotalIssued())).sum();
        double totalFinal = reports.stream().mapToDouble(r -> safe(r.getTotalFinal())).sum();
        double totalShort = reports.stream().mapToDouble(r -> safe(r.getTotalShort())).sum();

        double totalPercent = totalIssued > 0 ? (totalFinal / totalIssued) * 100 : 0;
        double totalShortPercent = totalIssued > 0 ? (totalShort / totalIssued) * 100 : 0;

        // Map each BormaReport to BormaReportResponseDTO
        List<BormaReportResponseDTO> rows = reports.stream().map(r -> {
            BormaReportResponseDTO dto = new BormaReportResponseDTO();
            dto.setId(r.getId());
            dto.setWholesFinalafterboramaPercent(r.getWholesFinalAfterBormaPercent());
            dto.setWholesShortafterboramaPercent(r.getWholesShortAfterBormaPercent());
            dto.setTotalPercentOfBrokensAfterBorma(r.getTotalPercentOfBrokensAfterBorma());
            dto.setTotalPercentOfShortBrokensAfterBorma(r.getTotalPercentOfShortBrokensAfterBorma());
            dto.setTotalIssued(r.getTotalIssued());
            dto.setTotalFinal(r.getTotalFinal());
            dto.setTotalShort(r.getTotalShort());
            dto.setTotalPercent(r.getTotalPercent());
            dto.setTotalShortPercent(r.getTotalShortPercent());
            return dto;
        }).toList();

        return new BormaDaySummaryResponse(
                date,
                totalIssued,
                totalFinal,
                totalShort,
                totalPercent,
                totalShortPercent,
                rows
        );
    }

    // ---------------- CALCULATIONS ----------------
    private void calculate(BormaReport r) {
        double wholesReceived = safe(r.getWholesReceived());
        double brokensReceived = safe(r.getBrokensReceived());

        double wholes = safe(r.getWholesCountAfterBorma());
        double wholesShort = safe(r.getWholesShortCountAfterBorma());

        double brokens = safe(r.getBrokensCountAfterBorma());
        double brokensShort = safe(r.getBrokensShortCountAfterBorma());

        if (wholesReceived > 0) {
            r.setWholesFinalAfterBormaPercent((wholes / wholesReceived) * 100);
            r.setWholesShortAfterBormaPercent((wholesShort / wholesReceived) * 100);
        }

        if (brokensReceived > 0) {
            r.setTotalPercentOfBrokensAfterBorma((brokens / brokensReceived) * 100);
            r.setTotalPercentOfShortBrokensAfterBorma((brokensShort / brokensReceived) * 100);
        }

        double totalIssued = wholesReceived + brokensReceived;
        double totalFinal = wholes + brokens;
        double totalShort = wholesShort + brokensShort;

        r.setTotalIssued(totalIssued);
        r.setTotalFinal(totalFinal);
        r.setTotalShort(totalShort);

        if (totalIssued > 0) {
            r.setTotalPercent((totalFinal / totalIssued) * 100);
            r.setTotalShortPercent((totalShort / totalIssued) * 100);
        }
    }

    private double safe(Double v) {
        return v == null ? 0 : v;
    }
}
