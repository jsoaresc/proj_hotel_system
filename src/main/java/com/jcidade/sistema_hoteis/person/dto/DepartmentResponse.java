package com.jcidade.sistema_hoteis.person.dto;

public record DepartmentResponse(
        Long id,
        String name,
        Long hotelId,
        String hotelName
) {
}