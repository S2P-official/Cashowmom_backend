package com.fictilecore.crm.fictilecoreCRM.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.fictilecore.crm.fictilecoreCRM.entity.Tenant;
import com.fictilecore.crm.fictilecoreCRM.service.TenantService;

import java.util.List;

@RestController
@RequestMapping("/api/tenants")
public class TenantController {

    @Autowired
    private TenantService tenantService;

    // ✅ Create a new Tenant
    @PostMapping
    public Tenant createTenant(@RequestBody Tenant tenant) {
        return tenantService.createTenant(tenant);
    }

    // ✅ Get all Tenants
    @GetMapping
    public List<Tenant> getAllTenants() {
        return tenantService.getAllTenants();
    }

    // ✅ Get Tenant by ID
    @GetMapping("/{id}")
    public Tenant getTenantById(@PathVariable Long id) {
        return tenantService.getTenantById(id)
                .orElseThrow(() -> new RuntimeException("Tenant not found with ID: " + id));
    }

    // ✅ Get Tenant by GST number
    @GetMapping("/gst/{gstNumber}")
    public Tenant getTenantByGst(@PathVariable String gstNumber) {
        return tenantService.getTenantByGst(gstNumber)
                .orElseThrow(() -> new RuntimeException("Tenant not found with GST: " + gstNumber));
    }

    // ✅ Update Tenant
    @PutMapping("/{id}")
    public Tenant updateTenant(@PathVariable Long id, @RequestBody Tenant tenant) {
        return tenantService.updateTenant(id, tenant);
    }

    // ✅ Delete Tenant
    @DeleteMapping("/{id}")
    public String deleteTenant(@PathVariable Long id) {
        tenantService.deleteTenant(id);
        return "Tenant deleted successfully";
    }
}
