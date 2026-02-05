package com.fictilecore.crm.fictilecoreCRM.service;
import com.fictilecore.crm.fictilecoreCRM.dto.PeelingReportDTO;
import com.fictilecore.crm.fictilecoreCRM.dto.PeelingReportDaySummaryResponse;
import com.fictilecore.crm.fictilecoreCRM.dto.PeelingReportResponse;
import com.fictilecore.crm.fictilecoreCRM.entity.Employee;
import com.fictilecore.crm.fictilecoreCRM.entity.PeelingReport;
import com.fictilecore.crm.fictilecoreCRM.entity.Tenant;
import com.fictilecore.crm.fictilecoreCRM.repository.EmployeeRepository;
import com.fictilecore.crm.fictilecoreCRM.repository.PeelingReportRepository;
import com.fictilecore.crm.fictilecoreCRM.repository.TenantRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class PeelingReportService {

    @Autowired
    private PeelingReportRepository peelingReportRepository;
    
    @Autowired
    private TenantRepository tenantRepository;

    @Autowired
    private EmployeeRepository employeeRepository;



    

    public List<PeelingReport> getReportsByTenant(Long tenantId) {
        return peelingReportRepository.findByTenant_Id(tenantId);
    }

      // ✅ GET REPORTS BY DATE (DAY SUMMARY)
    // -------------------------------------------------------
   public PeelingReportDaySummaryResponse getReportsByTenantAndDate(
            Long tenantId,
            LocalDate date
    ) {
        // 1️⃣ Fetch reports for tenant + date
        List<PeelingReport> reports =
                peelingReportRepository.findByTenant_IdAndDate(tenantId, date);

        // 2️⃣ Convert to DTOs
        List<PeelingReportDTO> reportDtos = reports.stream()
               .map(this::toDTO)
                .collect(Collectors.toList());

        // 3️⃣ Calculate total issued (or any other field for day total)
        double totalIssued = reports.stream()
                .mapToDouble(r -> r.getTotalIssued() != null ? r.getTotalIssued() : 0)
                .sum();

        // 4️⃣ Size-wise issued totals
        Map<String, Double> sizeWiseTotalIssued = reports.stream()
                .collect(Collectors.groupingBy(
                        PeelingReport::getSize,
                        Collectors.summingDouble(r -> r.getTotalIssued() != null ? r.getTotalIssued() : 0)
                ));

        // 5️⃣ Build summary response
        return new PeelingReportDaySummaryResponse(
                date,
                totalIssued,
                sizeWiseTotalIssued,
                reportDtos
        );
    }

    public void deleteReport(Long id) {
        peelingReportRepository.deleteById(id);
    }

     // ✅ GET ALL EXCEPT COMPLETED
    // -------------------------------------------------------
    public List<PeelingReport> getAllExceptCompletedReportsByTenant(Long tenantId) {
        return peelingReportRepository.findAllExceptCompletedByTenant(tenantId);
    }


    // -------------------------------------------------------
    // ✅ PATCH UPDATE (PARTIAL UPDATE)
    // -------------------------------------------------------
    public PeelingReport patchReport(Long id, PeelingReport updatedFields) {
        PeelingReport existing = peelingReportRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Report not found with id: " + id));


        if (updatedFields.getStatus() != null) existing.setStatus(updatedFields.getStatus());
        if (updatedFields.getTenant() != null) existing.setTenant(updatedFields.getTenant());
        if (updatedFields.getEmployee() != null) existing.setEmployee(updatedFields.getEmployee());

        return peelingReportRepository.save(existing);
    }

   // -------------------------------------------------------
    // ✅ GET REPORTS BY TENANT + EMPLOYEE (DTO)
    // -------------------------------------------------------
    public List<PeelingReportDTO> getReportsByTenantAndEmployee(
            Long tenantId,
            Long employeeId
    ) {
        List<PeelingReport> reports =
                peelingReportRepository.findByTenant_IdAndEmployee_Id(tenantId, employeeId);

        return reports.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

  

    // ✅ CREATE REPORTS
    // -------------------------------------------------------
   public PeelingReport createReport(
        Long tenantId,
        Long employeeId,
        PeelingReport report
) {
    Tenant tenant = tenantRepository.findById(tenantId)
            .orElseThrow(() ->
                    new RuntimeException("Tenant not found with ID: " + tenantId));

    Employee employee = null;
    if (employeeId != null) {
        employee = employeeRepository.findById(employeeId)
                .orElseThrow(() ->
                        new RuntimeException("Employee not found with ID: " + employeeId));
    }

    // ✅ SET RELATIONS
    report.setTenant(tenant);
    report.setEmployee(employee);

    // ✅ SAVE SINGLE ENTITY
    return peelingReportRepository.save(report);
}

    public PeelingReportDTO toDTO(PeelingReport report) {
        if (report == null) return null;

        PeelingReportDTO dto = new PeelingReportDTO();
        dto.setId(report.getId());
        dto.setDate(report.getDate());
        dto.setLotMark(report.getLotMark());
        dto.setOrigin(report.getOrigin());
        dto.setSize(report.getSize());
        dto.setIssuedWholes(report.getIssuedWholes());
        dto.setIssuedBroken(report.getIssuedBroken());
        dto.setTotalIssued(report.getTotalIssued());

        dto.setWWPeeledKgs(report.getWwPeeledKgs());

        dto.setAddTestKgs(report.getaddTestKgs());

        dto.setUnPeeledKgs(report.getUnPeeledKgs());

        dto.setBrokenAfterPeeling(report.getBrokenAfterPeeling());

        dto.setSau_Pl(report.getSauPl());

        dto.setRejectionAfterPeeled(report.getRejectionAfterPeeled());

         dto.setHusk(report.getHusk());
       
        
         dto.setTenantId(report.getTenantId());
        dto.setEmployeeId(report.getEmployee() != null ? report.getEmployee().getId() : null);

        return dto;
    }

public List<PeelingReportResponse> getReportsByTenantEmployeeAndDate(
        Long tenantId,
        Long employeeId,
        LocalDate date
) {
    return peelingReportRepository.findReportsByTenantEmployeeAndDate(tenantId,employeeId, date);
}
}


