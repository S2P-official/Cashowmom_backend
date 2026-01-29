package com.fictilecore.crm.fictilecoreCRM.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AttendanceStatusResponse {
    private boolean checkedIn;
    private boolean checkedOut;
}
