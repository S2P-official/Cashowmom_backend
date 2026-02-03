package com.fictilecore.crm.fictilecoreCRM.entity;

import com.fictilecore.crm.fictilecoreCRM.entity.base.BaseTenantEntity;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "peeling_report")

@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PeelingReport extends BaseTenantEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
   
    private Long id;
    private LocalDate date;
    private String lotMark;
    private String origin; // e.g., GHANA
    private String size;   // e.g., A,B,C,D
    private Double issuedWholes;
    private Double issuedBroken;
    private Double totalIssued;
    private Double wwPeeledKgs;
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getLotMark() {
        return lotMark;
    }

    public void setLotMark(String lotMark) {
        this.lotMark = lotMark;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public Double getIssuedWholes() {
        return issuedWholes;
    }

    public void setIssuedWholes(Double issuedWholes) {
        this.issuedWholes = issuedWholes;
    }

    public Double getIssuedBroken() {
        return issuedBroken;
    }

    public void setIssuedBroken(Double issuedBroken) {
        this.issuedBroken = issuedBroken;
    }

    public Double getTotalIssued() {
        return totalIssued;
    }

    public void setTotalIssued(Double totalIssued) {
        this.totalIssued = totalIssued;
    }

    public Double getWwPeeledKgs() {
        return wwPeeledKgs;
    }

    public void setWwPeeledKgs(Double wwPeeledKgs) {
        this.wwPeeledKgs = wwPeeledKgs;
    }

    public Double getaddTestKgs() {
        return addTestKgs;
    }

    public void setAddTestKgs(Double addTestKgs) {
        this.addTestKgs = addTestKgs;
    }

    public Double getUnPeeledKgs() {
        return unPeeledKgs;
    }

    public void setUnPeeledKgs(Double unPeeledKgs) {
        this.unPeeledKgs = unPeeledKgs;
    }

    public Double getBrokenAfterPeeling() {
        return brokenAfterPeeling;
    }

    public void setBrokenAfterPeeling(Double brokenAfterPeeling) {
        this.brokenAfterPeeling = brokenAfterPeeling;
    }

    public Double getSauPl() {
        return sauPl;
    }

    public void setSauPl(Double sauPl) {
        this.sauPl = sauPl;
    }

    public Double getRejectionAfterPeeled() {
        return rejectionAfterPeeled;
    }

    public void setRejectionAfterPeeled(Double rejectionAfterPeeled) {
        this.rejectionAfterPeeled = rejectionAfterPeeled;
    }

    public Double getHusk() {
        return husk;
    }

    public void setHusk(Double husk) {
        this.husk = husk;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    private Double addTestKgs;
    private Double unPeeledKgs;
    private Double brokenAfterPeeling;
    private Double sauPl;
    private Double rejectionAfterPeeled;
    private Double husk;
    private String status;
    



    // --- Whole / Broken details ---
    
    // --- Relations ---
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    @PrePersist
    protected void onCreate() {
        if (getTenantId() == null) {
            throw new IllegalStateException(
                "Tenant ID must be set before persisting PeelingReport"
            );
        }
    }

    public Object TotalPeelingResultl() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'TotalPeelingResultl'");
    }
}
