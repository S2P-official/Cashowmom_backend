package com.fictilecore.crm.fictilecoreCRM.mapper;

import com.fictilecore.crm.fictilecoreCRM.dto.BormaReportRequestDTO;
import com.fictilecore.crm.fictilecoreCRM.entity.BormaReport;
import com.fictilecore.crm.fictilecoreCRM.entity.Employee;
import com.fictilecore.crm.fictilecoreCRM.entity.Tenant;
import org.springframework.stereotype.Component;

@Component
public class BormaReportMapper {

    public BormaReport toEntity(
            BormaReportRequestDTO dto,
            Tenant tenant,
            Employee employee
    ) {

        BormaReport report = BormaReport.builder()
                .date(dto.getDate())
                .lot(dto.getLot())
                .size(dto.getSize())
                .origin(dto.getOrigin())
                .perBag(dto.getPerBag())

                .shelledKernelMoisture(dto.getShelledKernelMoisture())
                .afterBormaKernelMoisture(dto.getAfterBormaKernalMoisture())
                .bormaTimeDuration(dto.getAormaTimeDuration())
                .bormaTemperature(dto.getBormaTemperature())

                .wholesReceived(dto.getWholesReceived())
                .brokensReceived(dto.getBrokensReceived())

                .wholesCountAfterBorma(dto.getWholesCountAfterborama())
                .wholesShortCountAfterBorma(dto.getWholesShortCountAfterborama())

                .brokensCountAfterBorma(dto.getBrokensCountAfterBorma())
                .brokensShortCountAfterBorma(dto.getBrokensShortCountAfterBorma())

                .status("CREATED")
                .employee(employee)
                .build();

        // 🔑 BaseTenantEntity field
        report.setTenant(tenant);

        return report;
    }
}
