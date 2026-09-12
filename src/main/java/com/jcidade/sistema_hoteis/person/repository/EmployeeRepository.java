package com.jcidade.sistema_hoteis.person.repository;

import com.jcidade.sistema_hoteis.person.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    boolean existsByDocument(String document);

    boolean existsByEmployeeNumber(String employeeNumber);

    List<Employee> findByDepartmentId(Long departmentId);
}