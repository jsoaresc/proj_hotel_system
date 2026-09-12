package com.jcidade.sistema_hoteis.hotel.repository;

import com.jcidade.sistema_hoteis.hotel.entity.RoomType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomTypeRepository extends JpaRepository<RoomType, Long> {
    boolean existsByName(String name);
}