package com.gc.domain.usuario;

public record UsuarioListarDTO(
        String nombre,
        String clave,
        String curso,
        int cant_topicos,
        int cant_respuestas
) {
}
