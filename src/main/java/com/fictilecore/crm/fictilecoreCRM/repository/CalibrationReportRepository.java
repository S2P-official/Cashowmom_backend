package com.fictilecore.crm.fictilecoreCRM.repository;

import com.fictilecore.crm.fictilecoreCRM.entity.CalibrationReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface CalibrationReportRepository extends JpaRepository<CalibrationReport, Long> {

    // Get all reports of a tenant
    List<CalibrationReport> findByTenant_Id(Long tenantId);

    // Get all reports for a tenant and employee
    List<CalibrationReport> findByTenant_IdAndEmployee_Id(Long tenantId, Long employeeId);

    // Get all reports for a tenant on a specific date
    List<CalibrationReport> findByTenant_IdAndDate(Long tenantId, LocalDate date);

    // Get all reports except completed for a tenant
    @Query("SELECT r FROM CalibrationReport r WHERE r.tenant.id = :tenantId AND (r.status IS NULL OR r.status <> 'Completed')")
    List<CalibrationReport> findAllExceptCompletedByTenant(@Param("tenantId") Long tenantId);   
}
