package com.jcidade.sistema_hoteis.hotel.repository;

import com.jcidade.sistema_hoteis.hotel.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoomRepository extends JpaRepository<Room, Long> {
    boolean existsByNumberAndHotelId(String number, Long hotelId);
    List<Room> findByHotelId(Long hotelId);
}