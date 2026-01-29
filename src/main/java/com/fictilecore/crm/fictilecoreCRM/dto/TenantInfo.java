package com.fictilecore.crm.fictilecoreCRM.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TenantInfo {
    private Long id;
    private String tenantName;
}
