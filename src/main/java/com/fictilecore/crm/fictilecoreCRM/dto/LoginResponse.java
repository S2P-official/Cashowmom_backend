package com.fictilecore.crm.fictilecoreCRM.dto;

import lombok.Getter;
import lombok.Setter;
@Setter
@Getter
public class LoginResponse {
    private Long employee_id;
    private String employee_name;
    private String employee_role;
    private String token;
    private Long tenant_id;
    private String tenant_name;

    public LoginResponse(Long employee_id, String employee_name, String employee_role,
                         String token, Long tenant_id, String tenant_name) {
        this.employee_id = employee_id;
        this.employee_name = employee_name;
        this.employee_role = employee_role;
        this.token = token;
        this.tenant_id = tenant_id;
        this.tenant_name = tenant_name;
    }

    // Getters...
}
