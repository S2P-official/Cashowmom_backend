package com.fictilecore.crm.fictilecoreCRM.controller;

import com.fictilecore.crm.fictilecoreCRM.dto.EmployeeDTO;
import com.fictilecore.crm.fictilecoreCRM.dto.LoginRequest;
import com.fictilecore.crm.fictilecoreCRM.dto.LoginResponse;
import com.fictilecore.crm.fictilecoreCRM.entity.Employee;
import com.fictilecore.crm.fictilecoreCRM.entity.Tenant;
import com.fictilecore.crm.fictilecoreCRM.repository.EmployeeRepository;
import com.fictilecore.crm.fictilecoreCRM.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.YearMonth;
import java.util.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private EmployeeRepository employeeRepository;

    // Create Employee under a Tenant
    @PostMapping("/tenant/{tenantId}")
    public Employee createEmployee(@PathVariable Long tenantId, @RequestBody Employee employee) {
        return employeeService.createEmployee(tenantId, employee);
    }

    // Get all Employees 
    @GetMapping("/tenant/{tenantId}")
    public List<EmployeeDTO> getEmployeesByTenant(@PathVariable Long tenantId) {
        return employeeService.getEmployeesByTenant(tenantId);
    }


    // Get single Employee
    @GetMapping("/{id}")
    public EmployeeDTO getEmployeeById(@PathVariable Long id) {
        return employeeService.getEmployeeById(id);
    }

    // Update Employee
    @PutMapping("/{id}")
    public Employee updateEmployee(@PathVariable Long id, @RequestBody Employee employee) {
        return employeeService.updateEmployee(id, employee);
    }

    // Delete Employee
    @DeleteMapping("/{id}")
    public String deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
        return "Employee deleted successfully";
    }

    // Login
    @PostMapping("/login")
    public ResponseEntity<?> loginEmployee(@RequestBody LoginRequest loginRequest) {
        Employee employee = employeeService.findByEmailOrPhone(loginRequest.getIdentifier());

        if (employee == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not found");
        }

        if (!employee.getPassword().equals(loginRequest.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }

        String token = UUID.randomUUID().toString();
        employeeService.saveAuthToken(employee.getId(), token);

        Tenant tenant = employee.getTenant();

        LoginResponse response = new LoginResponse(
                employee.getId(),
                employee.getEmployee_name(),
                employee.getRole(),
                token,
                tenant != null ? tenant.getId() : null,
                tenant != null ? tenant.getTenantName() : null
        );

        return ResponseEntity.ok(response);
    }

    // Validate Token
    @GetMapping("/validate")
    public ResponseEntity<?> validateToken(@RequestHeader("Authorization") String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Missing or invalid Authorization header");
        }

        String token = authHeader.replace("Bearer ", "").trim();

        Optional<Employee> optionalEmployee = employeeRepository.findByAuthToken(token);
        if (optionalEmployee.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid or expired token");
        }

        Employee employee = optionalEmployee.get();
        Tenant tenant = employee.getTenant();

        Map<String, Object> response = new HashMap<>();
        response.put("valid", true);
        response.put("employee_id", employee.getId());
        response.put("employee_name", employee.getEmployee_name());
        response.put("employee_role", employee.getRole());
        response.put("tenant_id", tenant != null ? tenant.getId() : null);
        response.put("tenant_name", tenant != null ? tenant.getTenantName() : null);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/tenant/{tenantId}/today-present")
public List<EmployeeDTO> getTodaysPresentEmployees(@PathVariable Long tenantId) {
    return employeeService.getTodaysPresentEmployees(tenantId);
}

// In Controller
@PatchMapping("/employees/{id}/role")
public ResponseEntity<EmployeeDTO> updateRole(@PathVariable Long id, @RequestBody Map<String, String> updates) {
    String role = updates.get("role");
    Employee updated = employeeService.updateEmployeeRole(id, role);
    EmployeeDTO dto = employeeService.toDTO(updated, YearMonth.now().atDay(1), YearMonth.now().atEndOfMonth());
    return ResponseEntity.ok(dto);
}



}
