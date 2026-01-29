package com.fictilecore.crm.fictilecoreCRM.repository;

import com.fictilecore.crm.fictilecoreCRM.entity.BormaReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface BormaReportRepository extends JpaRepository<BormaReport, Long> {

    // Get all reports of a tenant
    List<BormaReport> findByTenant_Id(Long tenantId);

    // Get a single report by ID and tenant
    Optional<BormaReport> findByIdAndTenant_Id(Long id, Long tenantId);

    // Get all reports for a tenant and employee
    List<BormaReport> findByTenant_IdAndEmployee_Id(Long tenantId, Long employeeId);

    // Get all reports for a tenant on a specific date
    List<BormaReport> findByTenant_IdAndDate(Long tenantId, LocalDate date);

    // Get all reports except completed ones for a tenant
    @Query("SELECT b FROM BormaReport b WHERE b.tenant.id = :tenantId AND (b.status IS NULL OR b.status <> 'Completed')")
    List<BormaReport> findAllExceptCompletedByTenant(@Param("tenantId") Long tenantId);
    
    // ⚠️ Remove this incorrect method:
    // List<BormaReport> findByTenantIdAndEmployeeId(Long tenantId, Long employeeId);
}
