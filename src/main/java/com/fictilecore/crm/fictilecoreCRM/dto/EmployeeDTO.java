package com.fictilecore.crm.fictilecoreCRM.dto;

import lombok.*;

// ✅ Import TenantInfo if in another package
// import com.fictilecore.crm.fictilecoreCRM.dto.TenantInfo;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeDTO {
    private Long id;
    private String name;
    private String designation;
    private Double wage;
    private Long presentDays;
    private String role;
    private String email;
    private String phone;
    private TenantInfo tenant; // now visible
}
