package com.jcidade.sistema_hoteis.booking.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.util.List;

public record ReservationItemRequest(
        @NotNull(message = "Quarto é obrigatório") Long roomId,
        @NotNull(message = "Valor da diária é obrigatório")
        @Positive(message = "Valor da diária deve ser positivo")
        BigDecimal appliedDailyRate,

        @NotEmpty(message = "Cada quarto deve ter pelo menos um ocupante")
        @Valid List<OccupancyRequest> occupants
) {
}