package com.gc.controller;

import com.gc.domain.respuestas.*;
import com.gc.domain.topico.*;
import com.gc.domain.usuario.*;
import com.gc.infra.service.ObtenerUsuario;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/foro")
@SecurityRequirement(name = "bearer-key")
@Tag(name = "5. Borrar", description = "borrar usuario, topico y/o respuesta")
public class BorrarController {

    @Autowired
    private TopicoRepository topicoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RespuestaRepository respuestaRepository;

    private ObtenerUsuario obtenerUsuario;

    @Autowired
    public BorrarController(TopicoRepository topicoRepository
            , ObtenerUsuario obtenerUsuario
            , RespuestaRepository respuestaRepository
            , UsuarioRepository usuarioRepository) {

        this.topicoRepository = topicoRepository;
        this.obtenerUsuario = obtenerUsuario;
        this.respuestaRepository = respuestaRepository;
        this.usuarioRepository = usuarioRepository;

    }

    @DeleteMapping("/usuario-delete")
    @Transactional
    public ResponseEntity<String> eliminarUsuario() {

        UsuarioEntity usuarioEntity = this.obtenerUsuario.obtenerSub();

        usuarioEntity.getRespuestas().forEach(r -> this.respuestaRepository.delete(r));
        usuarioEntity.getTopicos().forEach(t -> this.topicoRepository.delete(t));

        this.usuarioRepository.delete(usuarioEntity);

        return new ResponseEntity<>("Usuario borrado con exito", HttpStatus.OK);

    }

    @DeleteMapping("/topico-{id}-delete")
    @Transactional
    public ResponseEntity<String> eliminarTopico(@PathVariable Long id) {

        TopicoEntity topicoEntity = this.obtenerUsuario.obtenerTopico(id);

        if (topicoEntity == null) {
            return new ResponseEntity<>("El topico no fue encontrado", HttpStatus.NOT_FOUND);
        }

        topicoEntity.getRespuestas().forEach(r -> this.respuestaRepository.delete(r));

        this.topicoRepository.delete(topicoEntity);

        return new ResponseEntity<>("El topico fue borrado con exito", HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/respuesta-{id}-delete")
    @Transactional
    public ResponseEntity<String> eliminarRespuesta(@PathVariable Long id) {

        RespuestasEntity respuestasEntity = this.obtenerUsuario.obtenerRespuesta(id);

        if (respuestasEntity == null) {
            return new ResponseEntity<>("La respuesta no fue encontrada", HttpStatus.NOT_FOUND);
        }

        this.respuestaRepository.delete(respuestasEntity);

        return new ResponseEntity<>("La respuesta fue borrada con exito", HttpStatus.NOT_FOUND);
    }
}
