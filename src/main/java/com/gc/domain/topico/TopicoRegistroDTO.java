package com.gc.domain.topico;

import jakarta.validation.constraints.NotBlank;

public record TopicoRegistroDTO(
        @NotBlank
        String titulo,
        @NotBlank
        String mensaje,
        @NotBlank
        String estatus
        ) {

        public TopicoRegistroDTO(TopicoDTO tDto) {
                this(tDto.titulo(),
                        tDto.mensaje(),
                        tDto.estatus());
        }
}
