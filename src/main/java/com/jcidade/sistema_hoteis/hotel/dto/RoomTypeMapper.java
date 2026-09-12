package com.jcidade.sistema_hoteis.hotel.dto;

import com.jcidade.sistema_hoteis.hotel.entity.RoomType;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RoomTypeMapper {
    RoomTypeResponse toResponse(RoomType entity);
    List<RoomTypeResponse> toResponseList(List<RoomType> entities);
    RoomType toEntity(RoomTypeRequest request);
    void updateEntityFromRequest(RoomTypeRequest request, @MappingTarget RoomType entity);
}