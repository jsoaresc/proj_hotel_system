package com.jcidade.sistema_hoteis.hotel.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
@Embeddable
public class Address {

    @NotBlank(message = "A rua é obrigatória")
    @Column(nullable = false)
    private String street;

    @NotBlank(message = "O número é obrigatório")
    @Column(nullable = false, length = 20)
    private String number;

    @Column(length = 100)
    private String complement;

    @NotBlank(message = "O bairro é obrigatório")
    @Column(nullable = false)
    private String neighborhood;

    @NotBlank(message = "A cidade é obrigatória")
    @Column(nullable = false)
    private String city;

    @NotBlank(message = "O estado é obrigatório")
    @Size(min = 2, max = 50, message = "O estado deve ser válido")
    @Column(nullable = false)
    private String state;

    @NotBlank(message = "O país é obrigatório")
    @Column(nullable = false)
    private String country;

    @NotBlank(message = "O CEP/Código Postal é obrigatório")
    @Column(nullable = false, length = 20)
    private String zipCode;
}
