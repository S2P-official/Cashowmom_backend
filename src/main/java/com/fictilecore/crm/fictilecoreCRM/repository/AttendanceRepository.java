package com.fictilecore.crm.fictilecoreCRM.repository;

import com.fictilecore.crm.fictilecoreCRM.entity.Attendance;
import com.fictilecore.crm.fictilecoreCRM.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {

    // ✅ Find today's attendance by employee
    Optional<Attendance> findByEmployeeAndDate(Employee employee, LocalDate date);

    // ✅ Find all attendance for employee between dates with minimum hours worked
    @Query("SELECT a.date FROM Attendance a " +
           "WHERE a.employee.id = :employeeId " +
           "AND a.date BETWEEN :startDate AND :endDate " +
           "AND a.totalHours >= :minHours")
    List<LocalDate> findPresentDatesByHours(
            @Param("employeeId") Long employeeId,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            @Param("minHours") double minHours
    );

    // ✅ Count present days for employee between dates
    @Query("SELECT COUNT(a) FROM Attendance a " +
           "WHERE a.employee.id = :employeeId " +
           "AND a.date BETWEEN :startDate AND :endDate " +
           "AND a.totalHours >= :minHours")
    Long countPresentDays(
            @Param("employeeId") Long employeeId,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            @Param("minHours") double minHours
    );

    // ✅ Find all attendance for a tenant on a specific date
    @Query("SELECT a FROM Attendance a " +
           "JOIN a.employee e " +
           "WHERE e.tenant.id = :tenantId " +
           "AND a.date = :date")
    List<Attendance> findByTenantIdAndDate(
            @Param("tenantId") Long tenantId,
            @Param("date") LocalDate date
    );

    @Modifying
@Query("DELETE FROM Attendance a WHERE a.employee.id = :empId")
void deleteByEmployeeId(@Param("empId") Long empId);

}
