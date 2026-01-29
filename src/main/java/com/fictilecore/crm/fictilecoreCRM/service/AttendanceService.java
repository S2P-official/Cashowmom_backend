package com.fictilecore.crm.fictilecoreCRM.service;

import com.fictilecore.crm.fictilecoreCRM.dto.AttendanceStatusResponse;
import com.fictilecore.crm.fictilecoreCRM.entity.Attendance;
import com.fictilecore.crm.fictilecoreCRM.entity.Employee;
import com.fictilecore.crm.fictilecoreCRM.entity.Tenant;
import com.fictilecore.crm.fictilecoreCRM.entity.subscription.Subscription;
import com.fictilecore.crm.fictilecoreCRM.repository.AttendanceRepository;
import com.fictilecore.crm.fictilecoreCRM.repository.EmployeeRepository;
import com.fictilecore.crm.fictilecoreCRM.repository.SubscriptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AttendanceService {

    private final AttendanceRepository attendanceRepository;
    private final EmployeeRepository employeeRepository;
    private final SubscriptionRepository subscriptionRepository;

    // -----------------------------
    // ✅ Validate Tenant Subscription
    // -----------------------------
    private void validateTenantSubscription(Tenant tenant) {
        Subscription subscription = subscriptionRepository
                .findTopByTenant_IdOrderByEndDateDesc(tenant.getId())
                .orElseThrow(() -> new RuntimeException("No subscription found for tenant"));

        if (!subscription.isActive() || subscription.getEndDate().isBefore(LocalDate.now())) {
            throw new RuntimeException("Tenant subscription inactive or expired");
        }
    }

    // -----------------------------
    // ✅ Employee Check-In
    // -----------------------------
 public Attendance markCheckIn(Long employeeId, Double latitude, Double longitude) {

    Employee employee = employeeRepository.findById(employeeId)
            .orElseThrow(() -> new RuntimeException("Employee not found"));

    Tenant tenant = employee.getTenant();
    validateTenantSubscription(tenant);

    // Prevent multiple check-ins
    attendanceRepository.findByEmployeeAndDate(employee, LocalDate.now())
            .ifPresent(a -> {
                throw new RuntimeException("Employee already checked in today");
            });

    Attendance attendance = Attendance.builder()
            .employee(employee)
            .date(LocalDate.now())
            .checkInTime(LocalDateTime.now())
            .checkInLatitude(latitude)
            .checkInLongitude(longitude)
            .build();

    // Set tenant via setter since builder cannot access inherited field
    attendance.setTenant(tenant);

    return attendanceRepository.save(attendance);
}

    // -----------------------------
    // ✅ Employee Check-Out
    // -----------------------------
    public Attendance markCheckOut(Long employeeId, Double latitude, Double longitude) {

        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        Tenant tenant = employee.getTenant();
        validateTenantSubscription(tenant);

        Attendance attendance = attendanceRepository.findByEmployeeAndDate(employee, LocalDate.now())
                .orElseThrow(() -> new RuntimeException("No check-in found for today"));

        if (attendance.getCheckOutTime() != null) {
            throw new RuntimeException("Employee already checked out today");
        }

        LocalDateTime checkOutTime = LocalDateTime.now();
        attendance.setCheckOutTime(checkOutTime);
        attendance.setCheckOutLatitude(latitude);
        attendance.setCheckOutLongitude(longitude);

        // Calculate total hours
        Duration duration = Duration.between(attendance.getCheckInTime(), checkOutTime);
        attendance.setTotalHours(duration.toMinutes() / 60.0);

        return attendanceRepository.save(attendance);
    }

    // -----------------------------
    // ✅ Get Attendance Status
    // -----------------------------
    public AttendanceStatusResponse getAttendanceStatus(Long employeeId) {

        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        validateTenantSubscription(employee.getTenant());

        return attendanceRepository.findByEmployeeAndDate(employee, LocalDate.now())
                .map(att -> new AttendanceStatusResponse(
                        att.getCheckInTime() != null,
                        att.getCheckOutTime() != null
                ))
                .orElse(new AttendanceStatusResponse(false, false));
    }

    // -----------------------------
    // ✅ Get Present Days
    // -----------------------------
    public List<LocalDate> getEmployeeAttendance(Long employeeId,
                                                 LocalDate startDate,
                                                 LocalDate endDate) {

        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("startDate cannot be after endDate");
        }

        final double MIN_PRESENT_HOURS = 6.5;

        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        validateTenantSubscription(employee.getTenant());

        return attendanceRepository.findPresentDatesByHours(
                employeeId, startDate, endDate, MIN_PRESENT_HOURS
        );
    }

    // -----------------------------
    // ✅ Reset Today Attendance
    // -----------------------------
    public void resetTodayAttendance(Long employeeId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        Attendance attendance = attendanceRepository.findByEmployeeAndDate(employee, LocalDate.now())
                .orElseThrow(() -> new RuntimeException("No attendance found for today"));

        attendanceRepository.delete(attendance);
    }
}
