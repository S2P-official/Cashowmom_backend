package com.fictilecore.crm.fictilecoreCRM.repository;

import com.fictilecore.crm.fictilecoreCRM.entity.PeelingReport;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface PeelingReportRepository extends JpaRepository<PeelingReport, Long> {

    // Get all reports for a tenant
    List<PeelingReport> findByTenant_Id(Long tenantId);

    // Get all reports for a tenant on a specific date
    List<PeelingReport> findByTenant_IdAndDate(Long tenantId, LocalDate date);
}
