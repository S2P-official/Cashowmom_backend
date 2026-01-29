package com.fictilecore.crm.fictilecoreCRM.controller;

import com.fictilecore.crm.fictilecoreCRM.dto.AttendanceRequest;
import com.fictilecore.crm.fictilecoreCRM.entity.Attendance;
import com.fictilecore.crm.fictilecoreCRM.service.AttendanceService;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/attendance")
@RequiredArgsConstructor
@CrossOrigin(origins = "*") // ✅ Allow frontend/mobile access
public class AttendanceController {

    private final AttendanceService attendanceService;

    /**
     * ✅ Employee Check-In
     * Example request (POST /api/attendance/checkin):
     * {
     *   "tenantId": 1,
     *   "employeeId": 5,
     *   "latitude": 15.8497,
     *   "longitude": 74.4977
     * }
     */
@PostMapping("/checkin")
public ResponseEntity<?> checkIn(@RequestBody AttendanceRequest request) {
    try {
        Attendance attendance = attendanceService.markCheckIn(
                request.getEmployeeId(),
                request.getLatitude(),
                request.getLongitude()
        );
        return ResponseEntity.ok(attendance);
    } catch (RuntimeException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}

    /**
     * ✅ Employee Check-Out
     * Example request (POST /api/attendance/checkout):
     * {
     *   "tenantId": 1,
     *   "employeeId": 5,
     *   "latitude": 15.8497,
     *   "longitude": 74.4977
     * }
     */
 @PostMapping("/checkout")
public ResponseEntity<?> checkOut(@RequestBody AttendanceRequest request) {
    try {
        Attendance attendance = attendanceService.markCheckOut(
                request.getEmployeeId(),
                request.getLatitude(),
                request.getLongitude()
        );
        return ResponseEntity.ok(attendance);
    } catch (RuntimeException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}




    /**
 * ✅ Check if employee has already checked in today
 * Example request (GET /api/attendance/status?tenantId=1&employeeId=5)
 */
@GetMapping("/status")
public ResponseEntity<?> checkAttendanceStatus(@RequestParam Long employeeId) {
    try {
        return ResponseEntity.ok(attendanceService.getAttendanceStatus(employeeId));
    } catch (RuntimeException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}


@GetMapping("/employee/{employeeId}")
public List<LocalDate> getEmployeeAttendance(
        @PathVariable Long employeeId,
        @RequestParam
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        LocalDate startDate,
        @RequestParam
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        LocalDate endDate
) {
    return attendanceService.getEmployeeAttendance(
            employeeId,
            startDate,
            endDate
    );
}


@DeleteMapping("/reset")
public ResponseEntity<?> resetTodayAttendance(@RequestParam Long employeeId) {
    try {
        attendanceService.resetTodayAttendance(employeeId);
        return ResponseEntity.ok("Attendance reset successfully");
    } catch (RuntimeException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}


}
