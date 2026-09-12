package com.jcidade.sistema_hoteis.hotel.dto;

public record HotelResponse(
        Long id,
        String name,
        String taxId,
        AddressDTO address,
        Long categoryId,
        String categoryName
) {
}