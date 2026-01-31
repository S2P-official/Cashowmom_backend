package com.fictilecore.crm.fictilecoreCRM.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fictilecore.crm.fictilecoreCRM.entity.BormaReport;

@Repository
public interface BormaReportRepository extends JpaRepository<BormaReport, Long>  {
       List<BormaReport> findByDate(LocalDate date);

    List<BormaReport> findByEmployee_Id(Long employeeId);

   List<BormaReport> findByTenant_Id(Long tenantId);

   List<BormaReport> findByTenant_IdAndEmployee_Id(Long tenantId, Long employeeId);

   List<BormaReport> findByTenant_IdAndDate(Long tenantId, LocalDate date);

}
