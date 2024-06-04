package com.gc.controller;

import com.gc.domain.respuestas.RespuestaDTO;
import com.gc.domain.respuestas.RespuestaRepository;
import com.gc.domain.respuestas.RespuestasEntity;
import com.gc.domain.respuestas.RespuestasRegistrarDTO;
import com.gc.domain.topico.TopicoDTO;
import com.gc.domain.topico.TopicoEntity;
import com.gc.domain.topico.TopicoRegistroDTO;
import com.gc.domain.topico.TopicoRepository;
import com.gc.domain.usuario.UsuarioDTO;
import com.gc.domain.usuario.UsuarioEntity;
import com.gc.domain.usuario.UsuarioRegistroDTO;
import com.gc.domain.usuario.UsuarioRepository;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Optional;


@RestController
@RequestMapping("/foro")
public class RegistrarController {

    @Autowired
    private TopicoRepository topicoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RespuestaRepository respuestaRepository;

    @PostMapping("/registrar-usuario")
    @Transactional
    public ResponseEntity registrarUsuario(@RequestBody @Valid UsuarioRegistroDTO usuarioRegistroDTO){

        Optional<UsuarioEntity> optionalUsuario = Optional.ofNullable(usuarioRepository.buscarUsuario(usuarioRegistroDTO.nombre()));

        if (!optionalUsuario.isPresent()) {

            UsuarioEntity usuarioEntity = new UsuarioEntity(new UsuarioDTO(usuarioRegistroDTO.nombre()
                                    , usuarioRegistroDTO.clave(), usuarioRegistroDTO.curso(), new ArrayList<TopicoEntity>()
                                    , new ArrayList<RespuestasEntity>()));

            var hashpass = BCrypt.hashpw(usuarioEntity.getPassword(), BCrypt.gensalt());

            usuarioEntity.setClave(hashpass);

            usuarioRepository.save(usuarioEntity);

            return new ResponseEntity<>("Usuario registrado exitosamente", HttpStatus.OK);
        }

        return new ResponseEntity<>("El usuario ya exite", HttpStatus.NOT_FOUND);
    }

    @PostMapping("/registrar-topico")
    @SecurityRequirement(name = "bearer-key")
    @Transactional
    public ResponseEntity<String> registrarTopico(@RequestBody @Valid TopicoRegistroDTO registroTopicoDTO){

        Optional<UsuarioEntity> optionalUsuario = Optional.ofNullable(usuarioRepository.buscarUsuario(registroTopicoDTO.usuario()));

        if (optionalUsuario.isPresent()) {

            LocalDate localDate = LocalDate.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

            TopicoDTO topicoDTO = new TopicoDTO(registroTopicoDTO.titulo(), registroTopicoDTO.mensaje(),
                    localDate.format(formatter), registroTopicoDTO.estatus(), optionalUsuario.get(), new ArrayList<RespuestasEntity>() {
            });

            TopicoEntity topicoEntity = new TopicoEntity(topicoDTO);

            optionalUsuario.get().agregarTopico(topicoEntity);

            usuarioRepository.save(optionalUsuario.get());
            topicoRepository.save(topicoEntity);

            return new ResponseEntity<>("Tópico creado con éxito", HttpStatus.OK);
        }

        return new ResponseEntity<>("El usuario no se encontro", HttpStatus.NOT_FOUND);
    }

    @PostMapping("/topico/{id}/registrar-respuestas")
    @SecurityRequirement(name = "bearer-key")
    @Transactional
    public ResponseEntity<String> resgistrarRespuesta(@PathVariable Long id, @RequestBody @Valid RespuestasRegistrarDTO respuestasRegistrarDTO){

        Optional<TopicoEntity> optionalTopico = topicoRepository.findById(id);

        if(optionalTopico.isPresent()){

            LocalDate localDate = LocalDate.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

            RespuestaDTO respuestaDTO = new RespuestaDTO(respuestasRegistrarDTO.titulo(), respuestasRegistrarDTO.respuesta(),
                    localDate.format(formatter), optionalTopico.get().getUsuario(), optionalTopico.get());

            RespuestasEntity respuestasEntity = new RespuestasEntity(respuestaDTO);

            TopicoEntity topicoEntity = optionalTopico.get();
            topicoEntity.agregarRespuesta(respuestasEntity);

            optionalTopico.get().getUsuario().agregarRespuesta(respuestasEntity);

            topicoRepository.save(topicoEntity);
            usuarioRepository.save(optionalTopico.get().getUsuario());
            respuestaRepository.save(new RespuestasEntity(respuestaDTO));

            return new ResponseEntity<>("Respuesta registrada con exito", HttpStatus.OK);
        }

        return new ResponseEntity<>("No se encontro el topico", HttpStatus.NOT_FOUND);
    }
}
