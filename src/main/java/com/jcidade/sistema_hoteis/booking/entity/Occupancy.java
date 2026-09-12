package com.jcidade.sistema_hoteis.booking.entity;

import com.jcidade.sistema_hoteis.core.entity.BaseEntity;
import com.jcidade.sistema_hoteis.person.entity.Guest;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "occupancy")
public class Occupancy extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reservation_item_id", nullable = false)
    private ReservationItem reservationItem;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "guest_id", nullable = false)
    private Guest guest;

    @Column(name = "is_room_responsible", nullable = false)
    private Boolean isRoomResponsible;
}