package com.fictilecore.crm.fictilecoreCRM.dto;

import lombok.Data;

/**
 * DTO for Check-In / Check-Out requests.
 * Used by AttendanceController to receive JSON input.
 */
@Data
public class AttendanceRequest {

    private Long tenantId;     // Tenant Identifier (multi-tenant support)
    private Long employeeId;   // Employee performing check-in/check-out
    private Double latitude;   // Current location latitude
    private Double longitude;  // Current location longitude
}
