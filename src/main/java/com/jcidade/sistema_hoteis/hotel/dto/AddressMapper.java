package com.jcidade.sistema_hoteis.hotel.dto;

import com.jcidade.sistema_hoteis.hotel.entity.Address;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AddressMapper {

    Address toEntity(AddressDTO dto);

    AddressDTO toDto(Address entity);
}