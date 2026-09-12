package com.jcidade.sistema_hoteis.booking.dto;

import jakarta.validation.constraints.NotNull;

public record OccupancyRequest(
        @NotNull(message = "Hóspede é obrigatório") Long guestId,
        @NotNull(message = "Indicador de responsável é obrigatório") Boolean isRoomResponsible
) {
}