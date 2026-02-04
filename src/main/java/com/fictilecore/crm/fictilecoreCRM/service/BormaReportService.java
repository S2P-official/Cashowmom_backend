package com.fictilecore.crm.fictilecoreCRM.service;

import com.fictilecore.crm.fictilecoreCRM.dto.BormaReportDTO;
import com.fictilecore.crm.fictilecoreCRM.dto.BormaReportDaySummaryResponse;
import com.fictilecore.crm.fictilecoreCRM.dto.BormaReportResponse;

import com.fictilecore.crm.fictilecoreCRM.entity.BormaReport;

import com.fictilecore.crm.fictilecoreCRM.entity.Employee;
import com.fictilecore.crm.fictilecoreCRM.entity.Tenant;
import com.fictilecore.crm.fictilecoreCRM.repository.BormaReportRepository;
import com.fictilecore.crm.fictilecoreCRM.repository.EmployeeRepository;
import com.fictilecore.crm.fictilecoreCRM.repository.TenantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BormaReportService {

    private final SubscriptionService subscriptionService;
    private final TenantRepository tenantRepository;
    private final EmployeeRepository employeeRepository;
    private final BormaReportRepository bormaReportRepository;
    

}
