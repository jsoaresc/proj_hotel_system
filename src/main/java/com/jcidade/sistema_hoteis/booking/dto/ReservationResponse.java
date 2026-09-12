package com.jcidade.sistema_hoteis.booking.dto;

import com.jcidade.sistema_hoteis.booking.entity.ReservationStatusEnum;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public record ReservationResponse(
        Long id,
        Long bookerId,
        String bookerName,
        Long hotelId,
        String hotelName,
        LocalDate expectedCheckinDate,
        LocalDate expectedCheckoutDate,
        ReservationStatusEnum status,
        BigDecimal totalAmount,
        List<ReservationItemResponse> items
) {
}