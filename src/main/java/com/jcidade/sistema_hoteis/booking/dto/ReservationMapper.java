package com.jcidade.sistema_hoteis.booking.dto;

import com.jcidade.sistema_hoteis.booking.entity.Reservation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = ReservationItemMapper.class)
public interface ReservationMapper {

    @Mapping(source = "booker.id", target = "bookerId")
    @Mapping(source = "booker.name", target = "bookerName")
    @Mapping(source = "hotel.id", target = "hotelId")
    @Mapping(source = "hotel.name", target = "hotelName")
    ReservationResponse toResponse(Reservation entity);

    List<ReservationResponse> toResponseList(List<Reservation> entities);
}