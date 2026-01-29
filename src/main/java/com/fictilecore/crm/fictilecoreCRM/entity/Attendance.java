package com.fictilecore.crm.fictilecoreCRM.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

import com.fictilecore.crm.fictilecoreCRM.entity.base.BaseTenantEntity;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Attendance extends BaseTenantEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate date;
    private LocalDateTime checkInTime;
    private LocalDateTime checkOutTime;

    private Double checkInLatitude;
    private Double checkInLongitude;

    private Double checkOutLatitude;
    private Double checkOutLongitude;

    private Double totalHours;

    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

}
