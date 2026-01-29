package com.fictilecore.crm.fictilecoreCRM.repository;

import com.fictilecore.crm.fictilecoreCRM.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    // Fixed method: navigate through tenant relationship
    List<Employee> findByTenant_Id(Long tenantId);

    Optional<Employee> findByEmail(String email);
    Optional<Employee> findByPhone(String phone);
    Optional<Employee> findByAuthToken(String token);
    boolean existsByTenant_IdAndEmail(Long tenantId, String email);
boolean existsByTenant_IdAndPhone(Long tenantId, String phone);

}
