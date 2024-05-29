package com.gc.domain.topico;

import com.gc.domain.respuestas.RespuestaListadoDTO;
import com.gc.domain.respuestas.RespuestasEntity;

import java.util.List;

public record TopicoListadoDTO(
        String titulo,
        String mensaje,
        String fecha,
        String estatus,
        String usuario,
        List<RespuestaListadoDTO> respuestas
) {

    public TopicoListadoDTO(TopicoDTO tDto, List<RespuestaListadoDTO> rLDto) {
        this(tDto.titulo(),
                tDto.mensaje(),
                tDto.fecha(),
                tDto.estatus(),
                tDto.usuario().getNombre(),
                rLDto);
    }
}
