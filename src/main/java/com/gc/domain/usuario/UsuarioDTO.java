package com.gc.domain.usuario;

import com.gc.domain.respuestas.RespuestasEntity;
import com.gc.domain.topico.TopicoEntity;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

public record UsuarioDTO(
        @NotBlank
        String nombre,
        @NotBlank
        String clave,
        @NotBlank
        String curso,
        List<TopicoEntity> topicos,
        List<RespuestasEntity> respuestas) {
}
