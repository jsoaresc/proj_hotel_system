package com.jcidade.sistema_hoteis.person.dto;

import com.jcidade.sistema_hoteis.hotel.dto.AddressDTO;

import java.time.LocalDate;

public record GuestResponse(
        Long id,
        String name,
        String document,
        LocalDate birthDate,
        String email,
        String phoneNumber,
        AddressDTO address
) {
}