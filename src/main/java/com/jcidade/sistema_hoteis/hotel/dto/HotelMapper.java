package com.jcidade.sistema_hoteis.hotel.dto;

import com.jcidade.sistema_hoteis.hotel.entity.Hotel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring", uses = AddressMapper.class)
public interface HotelMapper {

    @Mapping(source = "category.id", target = "categoryId")
    @Mapping(source = "category.name", target = "categoryName")
    HotelResponse toResponse(Hotel entity);

    List<HotelResponse> toResponseList(List<Hotel> entities);

    @Mapping(target = "category", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "rooms", ignore = true)
    Hotel toEntity(HotelRequest request);

    @Mapping(target = "category", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "rooms", ignore = true)
    void updateEntityFromRequest(HotelRequest request, @MappingTarget Hotel entity);
}