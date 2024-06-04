package com.gc.controller;

import com.gc.domain.respuestas.RespuestaRepository;
import com.gc.domain.respuestas.RespuestasEntity;
import com.gc.domain.topico.TopicoEntity;
import com.gc.domain.topico.TopicoRepository;
import com.gc.domain.usuario.UsuarioEntity;
import com.gc.domain.usuario.UsuarioRepository;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/foro")
@SecurityRequirement(name = "bearer-key")
public class BorrarController {

    @Autowired
    private TopicoRepository topicoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RespuestaRepository respuestaRepository;

    @DeleteMapping("/usuario-{id}-delete")
    @Transactional
    public ResponseEntity<String> eliminarUsuario(@PathVariable Long id){

        Optional<UsuarioEntity> optionalUsuario = usuarioRepository.findById(id);

        if (optionalUsuario.isPresent()) {

            UsuarioEntity usuario = optionalUsuario.get();

            usuario.getTopicos().forEach(t -> topicoRepository.delete(t));

            return new ResponseEntity<>("Usuario borrado con exito", HttpStatus.OK);
        }

        return new ResponseEntity<>("El usuario no fue encontrado", HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/topico-{id}-delete")
    @Transactional
    public ResponseEntity<String> eliminarTopico(@PathVariable Long id){
        Optional<TopicoEntity> optionalTopico = topicoRepository.findById(id);

        if (optionalTopico.isPresent()) {

            respuestaRepository.deleteByTopicoId(optionalTopico.get().getId());
            topicoRepository.delete(optionalTopico.get());

            return new ResponseEntity<>("El topico fue eliminado correctamente", HttpStatus.OK);
        }

        return new ResponseEntity<>("El topico no fue encontrado", HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/respuesta-{id}-delete")
    @Transactional
    public ResponseEntity<String> eliminarRespuesta(@PathVariable Long id){

        Optional<RespuestasEntity> optionalRespuestas = respuestaRepository.findById(id);

        if (optionalRespuestas.isPresent()) {

            respuestaRepository.delete(optionalRespuestas.get());

            return new ResponseEntity<>("La respuesta fue eliminada correctamente", HttpStatus.OK);
        }

        return new ResponseEntity<>("La respuesta no fue encontrada", HttpStatus.NOT_FOUND);
    }
}
