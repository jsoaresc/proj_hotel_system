package com.jcidade.sistema_hoteis.booking.entity;

import com.jcidade.sistema_hoteis.core.entity.BaseEntity;
import com.jcidade.sistema_hoteis.hotel.entity.Room;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "reservation_item")
public class ReservationItem extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reservation_id", nullable = false)
    private Reservation reservation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id", nullable = false)
    private Room room;

    @Column(name = "applied_daily_rate", nullable = false, precision = 10, scale = 2)
    private BigDecimal appliedDailyRate;

    @OneToMany(mappedBy = "reservationItem", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Occupancy> occupants = new ArrayList<>();
}