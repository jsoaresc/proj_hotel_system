package com.jcidade.sistema_hoteis.hotel.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record HotelRequest(
        @NotBlank(message = "Nome é obrigatório") String name,
        @NotBlank(message = "CNPJ é obrigatório") String taxId,
        @NotNull(message = "Endereço é obrigatório")
        @Valid AddressDTO address,
        @NotNull(message = "Categoria é obrigatória") Long categoryId
) {
}