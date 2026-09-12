package com.jcidade.sistema_hoteis.hotel.dto;

import com.jcidade.sistema_hoteis.hotel.entity.HotelCategory;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface HotelCategoryMapper {

    HotelCategoryResponse toResponse(HotelCategory entity);

    List<HotelCategoryResponse> toResponseList(List<HotelCategory> entities);

    HotelCategory toEntity(HotelCategoryRequest request);

    void updateEntityFromRequest(HotelCategoryRequest request, @MappingTarget HotelCategory entity);
}