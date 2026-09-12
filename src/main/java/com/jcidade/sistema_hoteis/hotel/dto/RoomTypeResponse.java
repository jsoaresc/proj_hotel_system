package com.jcidade.sistema_hoteis.hotel.dto;

import java.math.BigDecimal;

public record RoomTypeResponse(
        Long id,
        String name,
        Integer maxCapacity,
        BigDecimal baseDailyRate
) {
}