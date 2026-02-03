package com.fictilecore.crm.fictilecoreCRM.repository;
import com.fictilecore.crm.fictilecoreCRM.dto.CalibrationReportResponse;
import com.fictilecore.crm.fictilecoreCRM.dto.PeelingReportResponse;
import com.fictilecore.crm.fictilecoreCRM.entity.PeelingReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface PeelingReportRepository extends JpaRepository<PeelingReport, Long> {

    // Get all reports for a tenant
    List<PeelingReport> findByTenant_Id(Long tenantId);

    // Get all reports for a tenant on a specific date
    List<PeelingReport> findByTenant_IdAndDate(Long tenantId, LocalDate date);

    List<PeelingReport> findByTenant_IdAndEmployee_Id(Long tenantId, Long employeeId);

       // Get all reports except completed for a tenant
   
  @Query("SELECT r FROM PeelingReport r WHERE r.tenant.id = :tenantId AND (r.status IS NULL OR r.status <> 'Completed')")
    List<PeelingReport> findAllExceptCompletedByTenant(@Param("tenantId") Long tenantId);
@Query("""
    SELECT new com.fictilecore.crm.fictilecoreCRM.dto.PeelingReportResponse(
        c.id,
        c.date,
        c.lotMark,
        c.origin,
        c.size,
        c.issuedWholes,
        c.issuedBroken,
        c.totalIssued,
        c.wwPeeledKgs,
        c.addTestKgs,
        c.unPeeledKgs,
        c.brokenAfterPeeling,
        c.sauPl,
        c.rejectionAfterPeeled,
        c.husk,
        c.status,
        e.employee_name
    )
    FROM PeelingReport c
    JOIN c.employee e
    WHERE c.tenant.id = :tenantId
      AND e.id = :employeeId
      AND (:date IS NULL OR c.date = :date)
    ORDER BY c.date DESC
""")
List<PeelingReportResponse> findReportsByTenantEmployeeAndDate(
    Long tenantId,
    Long employeeId,
    LocalDate date
);





}


