package com.gc.domain.respuestas;

import com.gc.domain.topico.TopicoEntity;

public record RespuestasRegistrarDTO(
        String titulo,
        String respuesta
) {
    public RespuestasRegistrarDTO(RespuestaDTO respuestaDTO){
        this(respuestaDTO.titulo(),
                respuestaDTO.respuesta());
    }
}
