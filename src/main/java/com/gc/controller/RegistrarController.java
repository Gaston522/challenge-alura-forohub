package com.gc.controller;

import com.gc.domain.respuestas.*;
import com.gc.domain.topico.*;
import com.gc.domain.usuario.*;
import com.gc.infra.service.ObtenerUsuario;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Optional;


@RestController
@RequestMapping("/foro")
@Tag(name = "2. Registrar", description = "registrar usuario, topico y/o respuesta")
public class RegistrarController {

    private TopicoRepository topicoRepository;

    private UsuarioRepository usuarioRepository;

    private RespuestaRepository respuestaRepository;

    private ObtenerUsuario obtenerUsuario;

    @Autowired
    public RegistrarController(TopicoRepository topicoRepository
            , ObtenerUsuario obtenerUsuario
            , RespuestaRepository respuestaRepository
            , UsuarioRepository usuarioRepository) {

        this.topicoRepository = topicoRepository;
        this.obtenerUsuario = obtenerUsuario;
        this.respuestaRepository = respuestaRepository;
        this.usuarioRepository = usuarioRepository;

    }

    @PostMapping("/registrar-usuario")
    @Transactional
    public ResponseEntity<String> registrarUsuario(@RequestBody @Valid UsuarioRegistroDTO usuarioRegistroDTO) {

        Optional<UsuarioEntity> optionalUsuario = Optional.ofNullable(this.usuarioRepository.buscarUsuario(usuarioRegistroDTO.nombre()));

        if (!optionalUsuario.isPresent()) {

            UsuarioEntity usuarioEntity = new UsuarioEntity(new UsuarioDTO(usuarioRegistroDTO.nombre()
                    , usuarioRegistroDTO.clave(), usuarioRegistroDTO.curso(), new ArrayList<>()
                    , new ArrayList<>()));

            var hashpass = BCrypt.hashpw(usuarioEntity.getPassword(), BCrypt.gensalt());

            usuarioEntity.setClave(hashpass);

            this.usuarioRepository.save(usuarioEntity);

            return new ResponseEntity<>("Usuario registrado exitosamente", HttpStatus.OK);
        }

        return new ResponseEntity<>("El usuario ya exite", HttpStatus.NOT_FOUND);
    }

    @PostMapping("/registrar-topico")
    @SecurityRequirement(name = "bearer-key")
    @Transactional
    public ResponseEntity<String> registrarTopico(@RequestBody @Valid TopicoRegistroDTO registroTopicoDTO) {

        UsuarioEntity usuarioEntity = this.obtenerUsuario.obtenerSub();

        LocalDate localDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        TopicoDTO topicoDTO = new TopicoDTO(registroTopicoDTO.titulo(), registroTopicoDTO.mensaje(),
                localDate.format(formatter), registroTopicoDTO.estatus(), usuarioEntity, new ArrayList<>() {
        });

        TopicoEntity topicoEntity = new TopicoEntity(topicoDTO);

        this.topicoRepository.save(topicoEntity);

        return new ResponseEntity<>("Tópico creado con éxito", HttpStatus.OK);

    }

    @PostMapping("/topico/{id}/registrar-respuestas")
    @SecurityRequirement(name = "bearer-key")
    @Transactional
    public ResponseEntity<String> resgistrarRespuesta(@PathVariable Long id, @RequestBody @Valid RespuestasRegistrarDTO respuestasRegistrarDTO) {

        UsuarioEntity usuarioEntity = this.obtenerUsuario.obtenerSub();

        TopicoEntity topicoEntity = this.obtenerUsuario.obtenerTopico(id);

        if (topicoEntity == null) {
            return new ResponseEntity<>("No se encontro el topico", HttpStatus.NOT_FOUND);
        }

        Optional<TopicoEntity> optionalTopico = this.topicoRepository.findById(id);

        if (optionalTopico.isPresent()) {

            LocalDate localDate = LocalDate.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

            RespuestaDTO respuestaDTO = new RespuestaDTO(respuestasRegistrarDTO.titulo(), respuestasRegistrarDTO.respuesta(),
                    localDate.format(formatter), usuarioEntity, optionalTopico.get());

            RespuestasEntity respuestasEntity = new RespuestasEntity(respuestaDTO);

            topicoEntity.agregarRespuesta(respuestasEntity);

            usuarioEntity.agregarRespuesta(respuestasEntity);

            this.respuestaRepository.save(new RespuestasEntity(respuestaDTO));

            return new ResponseEntity<>("Respuesta registrada con exito", HttpStatus.OK);

        }

        return new ResponseEntity<>("El topico no fue encontrado", HttpStatus.NOT_FOUND);
    }

}
