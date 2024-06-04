package com.gc.domain.topico;

import com.gc.domain.usuario.UsuarioEntity;
import jakarta.validation.constraints.NotBlank;

public record TopicoRegistroDTO(
        @NotBlank
        String titulo,
        @NotBlank
        String mensaje,
        @NotBlank
        String estatus,
        @NotBlank
        String usuario
        ) {

        public TopicoRegistroDTO(TopicoDTO tDto) {
                this(tDto.titulo(),
                        tDto.mensaje(),
                        tDto.estatus(),
                        tDto.usuario().getNombre());
        }
}
