package com.jcidade.sistema_hoteis.booking.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.List;

public record ReservationRequest(
        @NotNull(message = "Hóspede responsável é obrigatório") Long bookerId,
        @NotNull(message = "Hotel é obrigatório") Long hotelId,

        @NotNull(message = "Data de check-in é obrigatória")
        @FutureOrPresent(message = "Check-in deve ser hoje ou no futuro")
        LocalDate expectedCheckinDate,

        @NotNull(message = "Data de check-out é obrigatória")
        @Future(message = "Check-out deve ser no futuro")
        LocalDate expectedCheckoutDate,

        @NotEmpty(message = "A reserva deve ter pelo menos um quarto")
        @Valid List<ReservationItemRequest> items
) {
}