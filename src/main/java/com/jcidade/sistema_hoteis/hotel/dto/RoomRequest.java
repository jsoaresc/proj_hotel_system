package com.jcidade.sistema_hoteis.hotel.dto;

import com.jcidade.sistema_hoteis.hotel.entity.RoomStatusEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RoomRequest(
        @NotBlank(message = "Número é obrigatório") String number,
        @NotNull(message = "Status de manutenção é obrigatório") RoomStatusEnum maintenanceStatus,
        @NotNull(message = "Tipo de quarto é obrigatório") Long typeId,
        @NotNull(message = "Hotel é obrigatório") Long hotelId
) {
}