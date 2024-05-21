package com.gc.domain;

import java.time.LocalDate;

public record RespuestaTopicoDTO(
        Long id,
        String titulo,
        String mensaje,
        String fecha,
        String estatus,
        String autor
) {
}
