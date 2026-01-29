package com.fictilecore.crm.fictilecoreCRM.entity.base;
import com.fictilecore.crm.fictilecoreCRM.entity.Tenant;

import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import com.fasterxml.jackson.annotation.JsonIgnore;

@MappedSuperclass
@Getter
@Setter
public abstract class BaseTenantEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "tenant_id", nullable = false, updatable = false)
    @JsonIgnore   // ⭐ REQUIRED
    private Tenant tenant;

    public Long getTenantId() {
        return tenant != null ? tenant.getId() : null;
    }
}
