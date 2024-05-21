package com.gc.domain;

import jakarta.validation.constraints.NotBlank;

import java.util.List;

public record UsuarioDTO(
        @NotBlank
        String nombre,
        @NotBlank
        String clave,
        @NotBlank
        String curso,
        List<TopicoEntity> topicos) {
}
