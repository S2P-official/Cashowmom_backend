package com.fictilecore.crm.fictilecoreCRM.service;

import com.fictilecore.crm.fictilecoreCRM.entity.Tenant;
import com.fictilecore.crm.fictilecoreCRM.repository.TenantRepository;
import com.fictilecore.crm.fictilecoreCRM.service.GSTValidator.GSTValidator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TenantService { 

    @Autowired
    private TenantRepository tenantRepository;

    // ✅ Create Tenant
    public Tenant createTenant(Tenant tenant) {

        // 1️⃣ GST Mandatory check
        if (tenant.getGstNumber() == null || tenant.getGstNumber().trim().isEmpty()) {
            throw new RuntimeException("GST number is mandatory");
        }

        // 2️⃣ GST format validation (Indian standard)
        if (!GSTValidator.isValid(tenant.getGstNumber())) {
            throw new RuntimeException("Invalid GST number format");
        }

        // Normalize GST
        tenant.setGstNumber(tenant.getGstNumber().toUpperCase().trim());

        // 3️⃣ Duplicate GST check
        if (tenantRepository.findByGstNumber(tenant.getGstNumber()).isPresent()) {
            throw new RuntimeException(
                "Tenant with GST number " + tenant.getGstNumber() + " already exists"
            );
        }

        // 4️⃣ CIN duplicate check (ONLY if provided)
        if (tenant.getCinNumber() != null && !tenant.getCinNumber().trim().isEmpty()) {

            tenant.setCinNumber(tenant.getCinNumber().trim());

            if (tenantRepository.findByCinNumber(tenant.getCinNumber()).isPresent()) {
                throw new RuntimeException(
                    "Tenant with CIN number " + tenant.getCinNumber() + " already exists"
                );
            }
        }

        return tenantRepository.save(tenant);
    }

    // ✅ Get all tenants
    public List<Tenant> getAllTenants() {
        return tenantRepository.findAll();
    }

    // ✅ Get tenant by ID
    public Optional<Tenant> getTenantById(Long id) {
        return tenantRepository.findById(id);
    }

    // ✅ Get tenant by GST
    public Optional<Tenant> getTenantByGst(String gstNumber) {
        return tenantRepository.findByGstNumber(gstNumber);
    }

    // ✅ Update tenant
    public Tenant updateTenant(Long id, Tenant updatedTenant) {
        return tenantRepository.findById(id)
                .map(existing -> {

                    // GST mandatory
                    if (updatedTenant.getGstNumber() == null ||
                        updatedTenant.getGstNumber().trim().isEmpty()) {
                        throw new RuntimeException("GST number is mandatory");
                    }

                    if (!GSTValidator.isValid(updatedTenant.getGstNumber())) {
                        throw new RuntimeException("Invalid GST number format");
                    }

                    String newGst = updatedTenant.getGstNumber().toUpperCase().trim();

                    // GST duplicate check
                    if (!newGst.equals(existing.getGstNumber()) &&
                        tenantRepository.findByGstNumber(newGst).isPresent()) {
                        throw new RuntimeException("GST number " + newGst + " already exists");
                    }

                    // CIN duplicate check (optional)
                    if (updatedTenant.getCinNumber() != null &&
                        !updatedTenant.getCinNumber().trim().isEmpty()) {

                        String newCin = updatedTenant.getCinNumber().trim();

                        if (!newCin.equals(existing.getCinNumber()) &&
                            tenantRepository.findByCinNumber(newCin).isPresent()) {
                            throw new RuntimeException("CIN number " + newCin + " already exists");
                        }

                        existing.setCinNumber(newCin);
                    } else {
                        existing.setCinNumber(null);
                    }

                    existing.setTenantName(updatedTenant.getTenantName());
                    existing.setGstNumber(newGst);
                    existing.setAddress(updatedTenant.getAddress());

                    return tenantRepository.save(existing);
                })
                .orElseThrow(() -> new RuntimeException("Tenant not found with ID: " + id));
    }

    // ✅ Delete tenant
    public void deleteTenant(Long id) {
        tenantRepository.deleteById(id);
    }
}
