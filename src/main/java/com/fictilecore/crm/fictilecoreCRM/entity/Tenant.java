package com.fictilecore.crm.fictilecoreCRM.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fictilecore.crm.fictilecoreCRM.entity.tenant.Address;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "tenants")
public class Tenant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String tenantName;

    @Column(length = 15)
    private String gstNumber;

    @Column(length = 21)
    private String cinNumber;

    @Embedded
    private Address address;

    @Column(updatable = false)
    private LocalDateTime registeredDate;

    @OneToMany(
        mappedBy = "tenant",
        cascade = CascadeType.ALL,
        orphanRemoval = true
    )
    @JsonIgnore
    private List<Employee> employees;

    @PrePersist
    protected void onCreate() {
        this.registeredDate = LocalDateTime.now();
    }
}
