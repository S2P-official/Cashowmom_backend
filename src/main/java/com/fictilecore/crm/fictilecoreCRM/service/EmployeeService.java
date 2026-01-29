package com.fictilecore.crm.fictilecoreCRM.service;

import com.fictilecore.crm.fictilecoreCRM.dto.EmployeeDTO;
import com.fictilecore.crm.fictilecoreCRM.dto.TenantInfo;
import com.fictilecore.crm.fictilecoreCRM.entity.Attendance;
import com.fictilecore.crm.fictilecoreCRM.entity.Employee;
import com.fictilecore.crm.fictilecoreCRM.entity.Tenant;
import com.fictilecore.crm.fictilecoreCRM.repository.AttendanceRepository;
import com.fictilecore.crm.fictilecoreCRM.repository.EmployeeRepository;
import com.fictilecore.crm.fictilecoreCRM.repository.TenantRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final TenantRepository tenantRepository;
    private final AttendanceRepository attendanceRepository;

    // ---------------- CREATE EMPLOYEE ----------------
    public Employee createEmployee(Long tenantId, Employee employee) {
        Tenant tenant = tenantRepository.findById(tenantId)
                .orElseThrow(() -> new RuntimeException("Tenant not found with ID: " + tenantId));
        employee.setTenant(tenant);

        boolean emailExists = employeeRepository.existsByTenant_IdAndEmail(tenantId, employee.getEmail());
        if (emailExists) throw new RuntimeException("Email already exists for another employee in this tenant");

        boolean phoneExists = employeeRepository.existsByTenant_IdAndPhone(tenantId, employee.getPhone());
        if (phoneExists) throw new RuntimeException("Phone number already exists for another employee in this tenant");

        return employeeRepository.save(employee);
    }

    // ---------------- GET EMPLOYEES WITH PRESENT DAYS ----------------
    public List<EmployeeDTO> getEmployeesByTenant(Long tenantId) {
        YearMonth currentMonth = YearMonth.now();
        LocalDate startDate = currentMonth.atDay(1);
        LocalDate endDate = currentMonth.atEndOfMonth();

        return employeeRepository.findByTenant_Id(tenantId)
                .stream()
                .map(emp -> toDTO(emp, startDate, endDate))
                .collect(Collectors.toList());
    }

    // ---------------- GET SINGLE EMPLOYEE ----------------
    public EmployeeDTO getEmployeeById(Long id) {
        Employee emp = employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found with ID: " + id));
        return toDTO(emp, YearMonth.now().atDay(1), YearMonth.now().atEndOfMonth());
    }

    // ---------------- CONVERT EMPLOYEE → DTO ----------------
    public EmployeeDTO toDTO(Employee emp, LocalDate startDate, LocalDate endDate) {
        Long presentDays = attendanceRepository.countPresentDays(
                emp.getId(),
                endDate,
                startDate,
                emp.getTenant().getId());

        return EmployeeDTO.builder()
                .id(emp.getId())
                .name(emp.getEmployee_name())
                .designation(emp.getDesignation())
                .wage(emp.getWage())
                .presentDays(presentDays)
                .role(emp.getRole())
                .email(emp.getEmail())
                .phone(emp.getPhone())
                .tenant(TenantInfo.builder()
                        .id(emp.getTenant().getId())
                        .tenantName(emp.getTenant().getTenantName())
                        .build())
                .build();
    }

    // ---------------- UPDATE EMPLOYEE ----------------
    public Employee updateEmployee(Long id, Employee updatedEmployee) {
        Employee existing = employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found with ID: " + id));

        existing.setEmployee_name(updatedEmployee.getEmployee_name());
        existing.setWage(updatedEmployee.getWage());
        return employeeRepository.save(existing);
    }

    // ---------------- UPDATE EMPLOYEE ROLE ----------------
    @Transactional
    public Employee updateEmployeeRole(Long employeeId, String role) {
        Employee existing = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found with ID: " + employeeId));
        existing.setRole(role);
        return employeeRepository.save(existing);
    }

    // ---------------- DELETE EMPLOYEE ----------------
    @Transactional
    public void deleteEmployee(Long id) {
        attendanceRepository.deleteByEmployeeId(id);
        employeeRepository.deleteById(id);
    }

    // ---------------- LOGIN HELPERS ----------------
    public Employee findByEmailOrPhone(String identifier) {
        return employeeRepository.findByEmail(identifier)
                .or(() -> employeeRepository.findByPhone(identifier))
                .orElse(null);
    }

    public void saveAuthToken(Long employeeId, String token) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));
        employee.setAuthToken(token);
        employeeRepository.save(employee);
    }

    public boolean isValidToken(String token) {
        if (token == null || token.isEmpty()) return false;
        return employeeRepository.findByAuthToken(token).isPresent();
    }

    // ---------------- TODAY'S PRESENT EMPLOYEES ----------------
    public List<EmployeeDTO> getTodaysPresentEmployees(Long tenantId) {
        LocalDate today = LocalDate.now();
        List<Attendance> todaysAttendance = attendanceRepository.findByTenantIdAndDate(tenantId, today);

        return todaysAttendance.stream()
                .map(att -> {
                    Employee emp = att.getEmployee();
                    return EmployeeDTO.builder()
                            .id(emp.getId())
                            .name(emp.getEmployee_name())
                            .tenant(emp.getTenant() != null
                                    ? new TenantInfo(emp.getTenant().getId(), emp.getTenant().getTenantName())
                                    : null)
                            .build();
                })
                .collect(Collectors.toList());
    }
}
