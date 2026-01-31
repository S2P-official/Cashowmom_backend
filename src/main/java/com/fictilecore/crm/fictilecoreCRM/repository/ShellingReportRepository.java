package com.fictilecore.crm.fictilecoreCRM.repository;

import com.fictilecore.crm.fictilecoreCRM.entity.ShellingReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface ShellingReportRepository extends JpaRepository<ShellingReport, Long> {

    // Get all reports for a tenant
    List<ShellingReport> findByTenant_Id(Long tenantId);

    // Get all reports for a tenant on a specific date
    List<ShellingReport> findByTenant_IdAndDate(Long tenantId, LocalDate date);

    // Get all reports for a tenant and employee
    List<ShellingReport> findByTenant_IdAndEmployee_Id(Long tenantId, Long employeeId);

    // Get all reports for a tenant between dates
    List<ShellingReport> findByTenant_IdAndDateBetween(Long tenantId, LocalDate start, LocalDate end);

    // Get all reports except completed ones for a tenant
    @Query("SELECT s FROM ShellingReport s " +
           "WHERE s.tenant.id = :tenantId " +
           "AND (s.status IS NULL OR s.status <> 'Completed')")
    List<ShellingReport> findAllExceptCompletedByTenant(@Param("tenantId") Long tenantId);

    
    // Tenant and Employee filtering with optional date
    @Query("""
        SELECT r FROM ShellingReport r
        WHERE r.tenant.id = :tenantId
          AND r.employee.id = :employeeId
          AND (:date IS NULL OR r.date = :date)
    """)
    List<ShellingReport> findReportsByTenantEmployeeAndDate(
        @Param("tenantId") Long tenantId,
        @Param("employeeId") Long employeeId,
        @Param("date") LocalDate date
    );
}
