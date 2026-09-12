package com.jcidade.sistema_hoteis.booking.dto;

import com.jcidade.sistema_hoteis.booking.entity.Occupancy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OccupancyMapper {

    @Mapping(source = "guest.id", target = "guestId")
    @Mapping(source = "guest.name", target = "guestName")
    OccupancyResponse toResponse(Occupancy entity);

    List<OccupancyResponse> toResponseList(List<Occupancy> entities);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "reservationItem", ignore = true)
    @Mapping(target = "guest", ignore = true)
    Occupancy toEntity(OccupancyRequest request);
}