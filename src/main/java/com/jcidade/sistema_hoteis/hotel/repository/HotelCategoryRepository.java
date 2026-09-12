package com.jcidade.sistema_hoteis.hotel.repository;

import com.jcidade.sistema_hoteis.hotel.entity.HotelCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HotelCategoryRepository extends JpaRepository<HotelCategory, Long> {
    boolean existsByName(String name);
}