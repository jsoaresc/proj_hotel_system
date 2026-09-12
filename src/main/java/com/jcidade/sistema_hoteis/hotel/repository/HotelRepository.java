package com.jcidade.sistema_hoteis.hotel.repository;

import com.jcidade.sistema_hoteis.hotel.entity.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HotelRepository extends JpaRepository<Hotel, Long> {
    boolean existsByTaxId(String taxId);
}