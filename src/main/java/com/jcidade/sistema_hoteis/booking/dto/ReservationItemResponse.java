package com.jcidade.sistema_hoteis.booking.dto;

import java.math.BigDecimal;
import java.util.List;

public record ReservationItemResponse(
        Long id,
        Long roomId,
        String roomNumber,
        BigDecimal appliedDailyRate,
        List<OccupancyResponse> occupants
) {
}