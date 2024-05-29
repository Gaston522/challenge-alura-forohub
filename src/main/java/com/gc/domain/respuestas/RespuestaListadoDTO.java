package com.gc.domain.respuestas;

public record RespuestaListadoDTO(
        String titulo,
        String respuesta,
        String fecha,
        String usuario,
        String topico
) {

    public RespuestaListadoDTO(RespuestasEntity respuestasEntity){
        this(respuestasEntity.getTitulo(),
                respuestasEntity.getRespuesta(),
                respuestasEntity.getFecha(),
                respuestasEntity.getUsuario().getNombre(),
                respuestasEntity.getTopico().getTitulo());
    }
}
