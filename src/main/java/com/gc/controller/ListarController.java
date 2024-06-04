package com.gc.controller;

import com.gc.domain.respuestas.*;
import com.gc.domain.topico.*;
import com.gc.domain.usuario.*;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/foro")
@SecurityRequirement(name = "bearer-key")
@Tag(name = "3. Listar", description = "listar usuario, topico y/o respuesta")
public class ListarController {

    @Autowired
    private TopicoRepository topicoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RespuestaRepository respuestaRepository;

    private UsuarioEntity usuarioEntity;

    @GetMapping("/usuario-listado")
    public ResponseEntity<List<UsuarioListarDTO>> listarUsuarios(){
        List<UsuarioListarDTO> usuarioListarDTO = usuarioRepository.findAll()
                .stream().map(u -> new UsuarioListarDTO(u.getNombre()
                        , u.getClave(), u.getCurso(), u.getTopicos().size()
                        , u.getRespuestas().size())).collect(Collectors.toList());

        return ResponseEntity.ok(usuarioListarDTO);
    }

    @GetMapping("/usuario-{id}")
    public ResponseEntity<UsuarioListarDTO> mostrarUsuarios(@PathVariable Long id){

        Optional<UsuarioEntity> optionalUsuario = usuarioRepository.findById(id);

        if (optionalUsuario.isPresent()) {
            UsuarioEntity u = optionalUsuario.get();
            UsuarioListarDTO usuarioListarDTO = new UsuarioListarDTO(u.getNombre(), u.getClave()
                    , u.getCurso(), u.getTopicos().size()
                    , u.getRespuestas().size());

            return ResponseEntity.ok(usuarioListarDTO);
        }

        return ResponseEntity.notFound().build();
    }

    @GetMapping("/topico-listado")
    public ResponseEntity<List<TopicoListadoDTO>> listarTopicos(){

        List<TopicoEntity> topicos = topicoRepository.findAll();

        List<TopicoListadoDTO> topicoListadoDTO = topicos.stream().map(topicoEntity -> {
            List<RespuestaListadoDTO> respuestaListadoDTO = topicoEntity.getRespuestas().stream()
                    .map(RespuestaListadoDTO::new)
                    .collect(Collectors.toList());

            TopicoDTO topicoDTO = new TopicoDTO(
                    topicoEntity.getTitulo(),
                    topicoEntity.getMensaje(),
                    topicoEntity.getFecha(),
                    topicoEntity.getEstatus(),
                    topicoEntity.getUsuario(),
                    topicoEntity.getRespuestas()
            );

            return new TopicoListadoDTO(topicoDTO, respuestaListadoDTO);
        }).collect(Collectors.toList());

        return ResponseEntity.ok(topicoListadoDTO);
    }

    @GetMapping("/topico-{id}")
    public ResponseEntity<TopicoListadoDTO> mostrarTopico(@PathVariable Long id){

        Optional<TopicoEntity> optionalTopico = topicoRepository.findById(id);

        if (optionalTopico.isPresent()) {
            List<RespuestasEntity> respuestasEntities = optionalTopico.get().getRespuestas();

            List<RespuestaListadoDTO> respuestaListadoDTO = respuestasEntities.stream()
                    .map(RespuestaListadoDTO::new).collect(Collectors.toList());

            TopicoListadoDTO topicoListadoDTO = new TopicoListadoDTO(optionalTopico.get().getTitulo()
                    , optionalTopico.get().getMensaje(), optionalTopico.get().getFecha()
                    , optionalTopico.get().getEstatus(), optionalTopico.get().getUsuario().getNombre()
                    , respuestaListadoDTO);

            return ResponseEntity.ok(topicoListadoDTO);
        }

        return ResponseEntity.notFound().build();
    }

    @GetMapping("/usuario-{id}-respuestas-listado")
    public ResponseEntity<List<RespuestaListadoDTO>> listarRespuestasPorUsuario(@PathVariable Long id){

        Optional<UsuarioEntity> usuario = usuarioRepository.findById(id);

        if (usuario.isPresent()) {
            List<RespuestaListadoDTO> respuestaListado = usuario.get().getRespuestas().stream().map(r -> new RespuestaListadoDTO(r.getTitulo()
                    , r.getRespuesta(), r.getFecha(), r.getUsuario().getNombre()
                    , r.getTopico().getTitulo())).collect(Collectors.toList());

            return ResponseEntity.ok(respuestaListado);
        }

        return ResponseEntity.notFound().build();
    }

    @GetMapping("/topico-{id}-respuestas-listado")
    public ResponseEntity<List<RespuestaListadoDTO>> listarRespuestasPorTopico(@PathVariable Long id){

        Optional<TopicoEntity> topico = topicoRepository.findById(id);

        if (topico.isPresent()) {
            List<RespuestaListadoDTO> respuestaListado = topico.get().getRespuestas().stream().map(r -> new RespuestaListadoDTO(r.getTitulo()
                    , r.getRespuesta(), r.getFecha(), r.getUsuario().getNombre()
                    , r.getTopico().getTitulo())).collect(Collectors.toList());

            return ResponseEntity.ok(respuestaListado);
        }

        return ResponseEntity.notFound().build();
    }

    @GetMapping("/respuestas-{id}")
    public ResponseEntity<RespuestaListadoDTO> mostrarRespuesta(@PathVariable Long id){

        Optional<RespuestasEntity> optionalRespuesta = respuestaRepository.findById(id);

        if (optionalRespuesta.isPresent()) {
            RespuestaListadoDTO respuestaListado = new RespuestaListadoDTO(optionalRespuesta.get().getTitulo()
                    , optionalRespuesta.get().getRespuesta(), optionalRespuesta.get().getFecha()
                    , optionalRespuesta.get().getUsuario().getNombre(), optionalRespuesta.get().getTopico().getTitulo());

            return ResponseEntity.ok(respuestaListado);
        }

        return ResponseEntity.notFound().build();
    }
}
