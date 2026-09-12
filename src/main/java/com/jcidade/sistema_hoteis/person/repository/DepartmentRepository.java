package com.jcidade.sistema_hoteis.person.repository;

import com.jcidade.sistema_hoteis.person.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DepartmentRepository extends JpaRepository<Department, Long> {

    boolean existsByNameAndHotelId(String name, Long hotelId);

    List<Department> findByHotelId(Long hotelId);
}