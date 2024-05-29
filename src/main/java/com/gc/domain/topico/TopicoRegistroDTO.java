package com.gc.domain.topico;

import com.gc.domain.usuario.UsuarioEntity;

public record TopicoRegistroDTO(
        String titulo,
        String mensaje,
        String estatus,
        String usuario
        ) {

        public TopicoRegistroDTO(TopicoDTO tDto) {
                this(tDto.titulo(),
                        tDto.mensaje(),
                        tDto.estatus(),
                        tDto.usuario().getNombre());
        }
}
