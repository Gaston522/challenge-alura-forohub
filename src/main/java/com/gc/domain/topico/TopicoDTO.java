package com.gc.domain.topico;

import com.gc.domain.respuestas.RespuestasEntity;
import com.gc.domain.usuario.UsuarioEntity;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

public record TopicoDTO(
        @NotBlank
        String titulo,
        @NotBlank
        String mensaje,
        @NotBlank
        String fecha,
        @NotBlank
        String estatus,
        @NotBlank
        UsuarioEntity usuario,
        List<RespuestasEntity> respuestas
) {
}
