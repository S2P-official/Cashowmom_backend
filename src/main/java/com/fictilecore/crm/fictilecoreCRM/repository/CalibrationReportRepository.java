package com.fictilecore.crm.fictilecoreCRM.repository;

import com.fictilecore.crm.fictilecoreCRM.dto.CalibrationReportResponse;
import com.fictilecore.crm.fictilecoreCRM.entity.CalibrationReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
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

    // ✅ Query to get reports mapped directly to CalibrationReportResponse DTO
    @Query("""
        SELECT new com.fictilecore.crm.fictilecoreCRM.dto.CalibrationReportResponse(
            c.id,
            c.date,
            c.lotMark,
            c.origin,
            c.perBagWeight,
            c.sizeRange,
            c.noOfBags,
            c.productionQty,
            c.countPerKg,
            e.employee_name
        )
        FROM CalibrationReport c
        LEFT JOIN c.employee e
        WHERE (:tenantId IS NULL OR c.tenant.id = :tenantId)
          AND (:employeeId IS NULL OR e.id = :employeeId)
          AND (:date IS NULL OR c.date = :date)
        ORDER BY c.date DESC
    """)
    List<CalibrationReportResponse> findReportsByTenantEmployeeAndDate(
        @Param("tenantId") Long tenantId,
        @Param("employeeId") Long employeeId,
        @Param("date") LocalDate date
    );


}
