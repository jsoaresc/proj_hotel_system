package com.jcidade.sistema_hoteis.hotel.dto;

import jakarta.validation.constraints.NotBlank;

public record AddressDTO(
        @NotBlank(message = "Rua é obrigatória") String street,
        @NotBlank(message = "Número é obrigatório") String number,
        String complement,
        @NotBlank(message = "Bairro é obrigatório") String neighborhood,
        @NotBlank(message = "Cidade é obrigatória") String city,
        @NotBlank(message = "Estado é obrigatório") String state,
        @NotBlank(message = "País é obrigatório") String country,
        @NotBlank(message = "CEP é obrigatório") String zipCode
) {
}
