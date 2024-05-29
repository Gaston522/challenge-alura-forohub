package com.gc.domain.respuestas;

import com.gc.domain.topico.TopicoEntity;
import com.gc.domain.usuario.UsuarioEntity;
import jakarta.validation.constraints.NotBlank;

public record RespuestaDTO(
        @NotBlank
        String titulo,
        @NotBlank
        String respuesta,
        @NotBlank
        String fecha,
        @NotBlank
        UsuarioEntity usuario,
        @NotBlank
        TopicoEntity topico
) {
}
