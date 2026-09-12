package com.jcidade.sistema_hoteis.hotel.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;

public record RoomTypeRequest(
        @NotBlank(message = "Nome é obrigatório") String name,
        @NotNull(message = "Capacidade máxima é obrigatória")
        @Positive(message = "Capacidade máxima deve ser positiva") Integer maxCapacity,
        @NotNull(message = "Valor da diária é obrigatório")
        @Positive(message = "Valor da diária deve ser positivo") BigDecimal baseDailyRate
) {
}