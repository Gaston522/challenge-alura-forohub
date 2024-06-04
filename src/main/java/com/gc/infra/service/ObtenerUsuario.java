package com.gc.infra.service;

import com.gc.domain.respuestas.RespuestasEntity;
import com.gc.domain.topico.TopicoEntity;
import com.gc.domain.usuario.*;
import com.gc.infra.security.TokenService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ObtenerUsuario {

    private HttpServletRequest request;

    private TokenService tokenService;

    private UsuarioRepository usuarioRepository;

    @Autowired
    public ObtenerUsuario(HttpServletRequest request
                            , TokenService tokenService
                            , UsuarioRepository usuarioRepository) {

        this.request = request;
        this.tokenService = tokenService;
        this.usuarioRepository = usuarioRepository;

    }

    public TopicoEntity obtenerTopico(Long id){

        UsuarioEntity usuarioEntity = obtenerSub();

        TopicoEntity topico = usuarioEntity.getTopicos().stream()
                .filter(t -> t.getId().equals(id))
                .findFirst()
                .orElse(null);

        return topico;

    }

    public RespuestasEntity obtenerRespuesta(Long id){

        UsuarioEntity usuarioEntity = obtenerSub();

        RespuestasEntity respuesta = usuarioEntity.getRespuestas().stream()
                .filter(r -> r.getId().equals(id))
                .findFirst()
                .orElse(null);

        return respuesta;

    }

    public UsuarioEntity obtenerSub() {

        var authHeader = this.request.getHeader("Authorization");

        var token = authHeader.replace("Bearer ", "");

        var nombreUsuario = this.tokenService.getSubject(token);

        return (UsuarioEntity) usuarioRepository.findByNombre(nombreUsuario);

    }
}
