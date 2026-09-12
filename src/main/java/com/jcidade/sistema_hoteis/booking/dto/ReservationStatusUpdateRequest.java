package com.jcidade.sistema_hoteis.booking.dto;

import com.jcidade.sistema_hoteis.booking.entity.ReservationStatusEnum;
import jakarta.validation.constraints.NotNull;

public record ReservationStatusUpdateRequest(
        @NotNull(message = "Novo status é obrigatório") ReservationStatusEnum status
) {
}