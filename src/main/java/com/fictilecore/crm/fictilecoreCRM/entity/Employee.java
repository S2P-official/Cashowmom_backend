package com.fictilecore.crm.fictilecoreCRM.entity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fictilecore.crm.fictilecoreCRM.entity.base.BaseTenantEntity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Employee extends BaseTenantEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String employee_name;
    private String designation;
    private Double wage;

    private String email;     // ✅ Added for login
    private String phone;     // ✅ Optional (login using phone)
    private String password;  // ✅ Added for login
    private String role;

    private LocalDateTime registeredDate;

    @Column(name = "auth_token")
    private String authToken;

    @PrePersist
    protected void onCreate() {
        this.registeredDate = LocalDateTime.now(); // automatically sets local date/time
    }
}
