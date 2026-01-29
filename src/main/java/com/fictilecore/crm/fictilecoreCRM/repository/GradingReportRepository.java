package com.fictilecore.crm.fictilecoreCRM.repository;

import com.fictilecore.crm.fictilecoreCRM.entity.GradingReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface GradingReportRepository extends JpaRepository<GradingReport, Long> {

    // Find all reports for a tenant
    @Query("SELECT g FROM GradingReport g WHERE g.tenant.id = :tenantId")
    List<GradingReport> findByTenantId(@Param("tenantId") Long tenantId);

    // Find reports by tenant and BormaReport date
    @Query("SELECT g FROM GradingReport g WHERE g.tenant.id = :tenantId AND g.bormaReport.date = :date")
    List<GradingReport> findByTenantIdAndBormaReport_Date(@Param("tenantId") Long tenantId,
                                                          @Param("date") LocalDate date);

    // Find reports by tenant and grade
    @Query("SELECT g FROM GradingReport g WHERE g.tenant.id = :tenantId AND g.grade = :grade")
    List<GradingReport> findByTenantIdAndGrade(@Param("tenantId") Long tenantId,
                                               @Param("grade") String grade);

    // Find by ID and tenant
    @Query("SELECT g FROM GradingReport g WHERE g.id = :reportId AND g.tenant.id = :tenantId")
    Optional<GradingReport> findByIdAndTenant_Id(@Param("reportId") Long reportId,
                                                 @Param("tenantId") Long tenantId);
}
