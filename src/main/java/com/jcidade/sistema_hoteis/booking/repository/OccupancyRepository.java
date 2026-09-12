package com.jcidade.sistema_hoteis.booking.repository;

import com.jcidade.sistema_hoteis.booking.entity.Occupancy;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OccupancyRepository extends JpaRepository<Occupancy, Long> {
}