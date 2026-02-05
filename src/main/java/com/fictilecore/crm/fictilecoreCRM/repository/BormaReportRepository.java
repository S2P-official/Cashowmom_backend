package com.fictilecore.crm.fictilecoreCRM.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.fictilecore.crm.fictilecoreCRM.entity.BormaReport;

@Repository
public interface BormaReportRepository extends JpaRepository<BormaReport, Long> {

  Optional<BormaReport> findByIdAndTenant_Id(Long id, Long tenantId);

  // Get all non-completed reports for a tenant
  @Query("SELECT r FROM BormaReport r WHERE r.tenant.id = :tenantId AND (r.status IS NULL OR r.status <> 'Completed')")
  List<BormaReport> findAllExceptCompletedByTenant(@Param("tenantId") Long tenantId);

  @Query("""
          SELECT r FROM BormaReport r
          WHERE r.tenant.id = :tenantId
            AND r.employee.id = :employeeId
            AND (:date IS NULL OR r.date = :date)
      """)
  List<BormaReport> findReportsByTenantEmployeeAndDate(
      @Param("tenantId") Long tenantId,
      @Param("employeeId") Long employeeId,
      @Param("date") LocalDate date);

}
