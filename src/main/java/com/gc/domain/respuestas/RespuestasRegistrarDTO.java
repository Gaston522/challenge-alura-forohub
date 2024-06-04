package com.gc.domain.respuestas;

import com.gc.domain.topico.TopicoEntity;
import jakarta.validation.constraints.NotBlank;

public record RespuestasRegistrarDTO(
        @NotBlank
        String titulo,
        @NotBlank
        String respuesta
) {
    public RespuestasRegistrarDTO(RespuestaDTO respuestaDTO){
        this(respuestaDTO.titulo(),
                respuestaDTO.respuesta());
    }
}
