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



}
