package com.jcidade.sistema_hoteis.booking.dto;

public record OccupancyResponse(
        Long id,
        Long guestId,
        String guestName,
        Boolean isRoomResponsible
) {
}