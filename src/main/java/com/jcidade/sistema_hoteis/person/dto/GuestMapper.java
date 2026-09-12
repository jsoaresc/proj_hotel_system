package com.jcidade.sistema_hoteis.person.dto;

import com.jcidade.sistema_hoteis.hotel.dto.AddressMapper;
import com.jcidade.sistema_hoteis.person.entity.Guest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring", uses = AddressMapper.class)
public interface GuestMapper {

    GuestResponse toResponse(Guest entity);

    List<GuestResponse> toResponseList(List<Guest> entities);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Guest toEntity(GuestRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateEntityFromRequest(GuestRequest request, @MappingTarget Guest entity);
}