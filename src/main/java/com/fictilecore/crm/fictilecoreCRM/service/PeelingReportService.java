package com.fictilecore.crm.fictilecoreCRM.service;

import com.fictilecore.crm.fictilecoreCRM.entity.PeelingReport;


import com.fictilecore.crm.fictilecoreCRM.repository.PeelingReportRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;

@Service
public class PeelingReportService {

    @Autowired
    private PeelingReportRepository peelingReportRepository;



    

    public List<PeelingReport> getReportsByTenant(Long tenantId) {
        return peelingReportRepository.findByTenant_Id(tenantId);
    }

    public List<PeelingReport> getReportsByDate(Long tenantId, LocalDate date) {
        return peelingReportRepository.findByTenant_IdAndDate(tenantId, date);
    }

    public void deleteReport(Long id) {
        peelingReportRepository.deleteById(id);
    }

    public List<PeelingReport> getAllExceptCompletedReportsByTenant(Long tenantId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAllExceptCompletedReportsByTenant'");
    }

    public PeelingReport patchReport(Long id, PeelingReport updatedFields) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'patchReport'");
    }

    public List<PeelingReport> getReportsByTenantAndEmployee(Long tenantId, Long employeeId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getReportsByTenantAndEmployee'");
    }

    public PeelingReport createReport(Long tenantId, Long employeeId, PeelingReport report) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'createReport'");
    }
}
