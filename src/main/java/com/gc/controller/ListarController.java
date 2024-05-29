package com.gc.controller;

import com.gc.domain.respuestas.RespuestaDTO;
import com.gc.domain.respuestas.RespuestaListadoDTO;
import com.gc.domain.respuestas.RespuestaRepository;
import com.gc.domain.respuestas.RespuestasEntity;
import com.gc.domain.topico.TopicoDTO;
import com.gc.domain.topico.TopicoEntity;
import com.gc.domain.topico.TopicoListadoDTO;
import com.gc.domain.topico.TopicoRepository;
import com.gc.domain.usuario.UsuarioDTO;
import com.gc.domain.usuario.UsuarioEntity;
import com.gc.domain.usuario.UsuarioListarDTO;
import com.gc.domain.usuario.UsuarioRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/foro")
public class ListarController {

    @Autowired
    private TopicoRepository topicoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RespuestaRepository respuestaRepository;

    private UsuarioEntity usuarioEntity;

    @GetMapping("/usuario/listado")
    public ResponseEntity<List<UsuarioListarDTO>> listarUsuarios(){
        List<UsuarioListarDTO> usuarioListarDTO = usuarioRepository.findAll()
                .stream().map(u -> new UsuarioListarDTO(u.getNombre()
                        , u.getClave(), u.getCurso(), u.getTopicos().size()
                        , u.getRespuestas().size())).collect(Collectors.toList());

        return ResponseEntity.ok(usuarioListarDTO);
    }

    @GetMapping("/usuario/{id}")
    public ResponseEntity<UsuarioListarDTO> mostrarUsuarios(@PathVariable Long id){

        Optional<UsuarioEntity> optionalUsuario = usuarioRepository.findById(id);

            UsuarioEntity u = optionalUsuario.get();
            UsuarioListarDTO usuarioListarDTO = new UsuarioListarDTO(u.getNombre(), u.getClave()
                                            , u.getCurso(), u.getTopicos().size()
                                            , u.getRespuestas().size());

            Optional<UsuarioListarDTO> optionalUsuarioListar = Optional.of(usuarioListarDTO);

            return ResponseEntity.of(optionalUsuarioListar);
    }

    @GetMapping("/topico/listado")
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

    @GetMapping("/topico/{id}")
    public ResponseEntity<TopicoListadoDTO> mostrarTopico(@PathVariable Long id){

        Optional<TopicoEntity> optionalTopico = topicoRepository.findById(id);

        List<RespuestasEntity> respuestasEntities = optionalTopico.get().getRespuestas();

        List<RespuestaListadoDTO> respuestaListadoDTO = respuestasEntities.stream()
                .map(RespuestaListadoDTO::new).collect(Collectors.toList());

        Optional<TopicoListadoDTO> optionalListado = optionalTopico.map(t -> new TopicoListadoDTO(t.getTitulo()
                , t.getMensaje(), t.getFecha()
                , t.getEstatus(), t.getUsuario().getNombre()
                , respuestaListadoDTO));

        return ResponseEntity.of(optionalListado);
    }

    @GetMapping("/respuestas/listado")
    public ResponseEntity<List<RespuestaListadoDTO>> listarRespuestas(){

        List<RespuestasEntity> respuestasEntity = respuestaRepository.findAll();

        List<RespuestaListadoDTO> respuestaListado = respuestasEntity.stream().map(r -> new RespuestaListadoDTO(r.getTitulo()
                                                    , r.getRespuesta(), r.getFecha(), r.getUsuario().getNombre()
                                                    , r.getTopico().getTitulo())).collect(Collectors.toList());

        return ResponseEntity.ok(respuestaListado);
    }

    @GetMapping("/respuestas/{id}")
    public ResponseEntity<RespuestaListadoDTO> mostrarRespuesta(@PathVariable Long id){

        Optional<RespuestasEntity> optionalRespuesta = respuestaRepository.findById(id);

        Optional<RespuestaListadoDTO> optionalRespuesaListado = optionalRespuesta.map(r -> new RespuestaListadoDTO(r.getTitulo()
                                , r.getRespuesta(), r.getFecha(), r.getUsuario().getNombre(), r.getTopico().getTitulo()));

        return ResponseEntity.of(optionalRespuesaListado);
    }
}
