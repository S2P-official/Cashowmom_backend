package com.fictilecore.crm.fictilecoreCRM.controller;

import com.fictilecore.crm.fictilecoreCRM.dto.BormaReportDTO;
import com.fictilecore.crm.fictilecoreCRM.dto.BormaReportResponse;
import com.fictilecore.crm.fictilecoreCRM.dto.ShellingReportDTO;
import com.fictilecore.crm.fictilecoreCRM.entity.BormaReport;
import com.fictilecore.crm.fictilecoreCRM.service.BormaReportService;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/borma-reports")
@RequiredArgsConstructor
public class BormaReportController {

        @Autowired
    private final BormaReportService bormaReportService;



}

