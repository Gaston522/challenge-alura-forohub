package com.gc.domain;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.List;

public record RegistroTopicoDTO(

        @NotBlank
        String titulo,
        @NotBlank
        String mensaje,
        @NotBlank
        String fecha,
        @NotBlank
        String estatus,
        @NotBlank
        UsuarioEntity autor
        ) {
}
