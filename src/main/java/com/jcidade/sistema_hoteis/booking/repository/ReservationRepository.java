package com.jcidade.sistema_hoteis.booking.repository;

import com.jcidade.sistema_hoteis.booking.entity.Reservation;
import com.jcidade.sistema_hoteis.booking.entity.ReservationStatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    List<Reservation> findByBookerId(Long bookerId);

    List<Reservation> findByHotelId(Long hotelId);

    List<Reservation> findByStatus(ReservationStatusEnum status);
}