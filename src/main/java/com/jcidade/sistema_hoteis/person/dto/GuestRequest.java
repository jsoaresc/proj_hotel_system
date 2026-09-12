package com.jcidade.sistema_hoteis.person.dto;

import com.jcidade.sistema_hoteis.hotel.dto.AddressDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record GuestRequest(
        @NotBlank(message = "Nome é obrigatório")
        @Size(max = 150, message = "Nome deve ter no máximo 150 caracteres")
        String name,

        @NotBlank(message = "Documento é obrigatório")
        @Size(max = 100, message = "Documento deve ter no máximo 100 caracteres")
        String document,

        @NotNull(message = "Data de nascimento é obrigatória")
        @Past(message = "Data de nascimento deve ser no passado")
        LocalDate birthDate,

        @NotBlank(message = "E-mail é obrigatório")
        @Email(message = "E-mail inválido")
        @Size(max = 150, message = "E-mail deve ter no máximo 150 caracteres")
        String email,

        @NotBlank(message = "Telefone é obrigatório")
        @Size(max = 30, message = "Telefone deve ter no máximo 30 caracteres")
        String phoneNumber,

        @NotNull(message = "Endereço é obrigatório")
        @Valid AddressDTO address
) {
}