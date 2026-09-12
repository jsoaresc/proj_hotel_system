package com.jcidade.sistema_hoteis.booking.dto;

import com.jcidade.sistema_hoteis.booking.entity.ReservationItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = OccupancyMapper.class)
public interface ReservationItemMapper {

    @Mapping(source = "room.id", target = "roomId")
    @Mapping(source = "room.number", target = "roomNumber")
    ReservationItemResponse toResponse(ReservationItem entity);

    List<ReservationItemResponse> toResponseList(List<ReservationItem> entities);
}