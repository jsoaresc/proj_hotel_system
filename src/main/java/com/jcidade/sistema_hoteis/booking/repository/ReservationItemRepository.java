package com.jcidade.sistema_hoteis.booking.repository;

import com.jcidade.sistema_hoteis.booking.entity.ReservationItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;

public interface ReservationItemRepository extends JpaRepository<ReservationItem, Long> {

    @Query("""
            SELECT COUNT(ri) > 0 FROM ReservationItem ri
            WHERE ri.room.id = :roomId
              AND ri.reservation.status IN (
                  com.jcidade.sistema_hoteis.booking.entity.ReservationStatusEnum.PENDING,
                  com.jcidade.sistema_hoteis.booking.entity.ReservationStatusEnum.CONFIRMED,
                  com.jcidade.sistema_hoteis.booking.entity.ReservationStatusEnum.IN_PROGRESS
              )
              AND ri.reservation.expectedCheckinDate < :checkout
              AND ri.reservation.expectedCheckoutDate > :checkin
            """)
    boolean existsOverlappingReservation(
            @Param("roomId") Long roomId,
            @Param("checkin") LocalDate checkin,
            @Param("checkout") LocalDate checkout
    );
}