package com.fictilecore.crm.fictilecoreCRM.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fictilecore.crm.fictilecoreCRM.dto.BormaReportResponse;
import com.fictilecore.crm.fictilecoreCRM.entity.BormaReport;
import com.fictilecore.crm.fictilecoreCRM.entity.RoastingReport;
import com.fictilecore.crm.fictilecoreCRM.entity.Tenant;

@Repository
public interface BormaReportRepository extends JpaRepository<BormaReport, Long>  {
       List<BormaReport> findByDate(LocalDate date);

    List<BormaReport> findByEmployee_Id(Long employeeId);

   List<BormaReport> findByTenant_Id(Long tenantId);

   List<BormaReport> findByTenant_IdAndEmployee_Id(Long tenantId, Long employeeId);

   List<BormaReport> findByTenant_IdAndDate(Long tenantId, LocalDate date);

      // Get all reports except completed for a tenant
    @Query("SELECT r FROM BormaReport r WHERE r.tenant.id = :tenantId AND (r.status IS NULL OR r.status <> 'Completed')")
    List<BormaReport> findAllExceptCompletedByTenant(@Param("tenantId") Long tenantId);

    
@Query("""
SELECT new com.fictilecore.crm.fictilecoreCRM.dto.BormaReportResponse(
    c.id,
    c.date,
    c.lotMark,
    c.origin,
    c.sizeRange,
    c.afterBormaKernelMoisture,
    c.BormaTimeDuration,
    c.BormaTemperature,
    c.aftrBormaWholes,
    c.aftrBormaBrokens,
    c.shortWholes,
    c.shortBrokens,
    c.totalWholes,
    c.totalShort,
    null,
    e.employee_name
)
FROM BormaReport c
LEFT JOIN c.employee e
WHERE (:tenantId IS NULL OR c.tenant.id = :tenantId)
  AND (:employeeId IS NULL OR e.id = :employeeId)
  AND (:date IS NULL OR c.date = :date)
ORDER BY c.date DESC
""")

List<BormaReportResponse> findReportsByTenantEmployeeAndDate(
    @Param("tenantId") Long tenantId,
    @Param("employeeId") Long employeeId,
    @Param("date") LocalDate date
);

  Optional<BormaReport> findByIdAndTenant_Id(Long reportId, Long tenantId);


}
