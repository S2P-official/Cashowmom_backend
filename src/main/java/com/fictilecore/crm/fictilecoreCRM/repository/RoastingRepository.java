package com.fictilecore.crm.fictilecoreCRM.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.fictilecore.crm.fictilecoreCRM.entity.RoastingReport;

public interface RoastingRepository extends JpaRepository<RoastingReport, Long> {

    // Get all reports for a tenant
    List<RoastingReport> findByTenant_Id(Long tenantId);

    // Get all reports for a tenant on a specific date
    List<RoastingReport> findByTenant_IdAndDate(Long tenantId, LocalDate date);

    // Get all reports for a tenant by employee
    List<RoastingReport> findByTenant_IdAndEmployee_Id(Long tenantId, Long employeeId);

    // Get a specific report for a tenant by report ID
    Optional<RoastingReport> findByIdAndTenant_Id(Long reportId, Long tenantId);

    // Get all non-completed reports for a tenant
    @Query("SELECT r FROM RoastingReport r WHERE r.tenant.id = :tenantId AND (r.status IS NULL OR r.status <> 'Completed')")
    List<RoastingReport> findAllExceptCompletedByTenant(@Param("tenantId") Long tenantId);
}
