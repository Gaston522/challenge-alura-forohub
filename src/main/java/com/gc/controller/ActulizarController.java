package com.gc.controller;

import com.gc.domain.respuestas.RespuestaListadoDTO;
import com.gc.domain.respuestas.RespuestaRepository;
import com.gc.domain.respuestas.RespuestasActualizarDTO;
import com.gc.domain.respuestas.RespuestasEntity;
import com.gc.domain.topico.TopicoActualizarDTO;
import com.gc.domain.topico.TopicoEntity;
import com.gc.domain.topico.TopicoListadoDTO;
import com.gc.domain.topico.TopicoRepository;
import com.gc.domain.usuario.UsuarioActualizarDTO;
import com.gc.domain.usuario.UsuarioEntity;
import com.gc.domain.usuario.UsuarioRepository;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/foro")
@SecurityRequirement(name = "bearer-key")
public class ActulizarController {

    @Autowired
    private TopicoRepository topicoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RespuestaRepository respuestaRepository;

    @PutMapping("/usuario-{id}-actualizar")
    @Transactional
    public ResponseEntity<String> actualizarUsuario(@PathVariable Long id,
                                                    @RequestBody UsuarioActualizarDTO actualizarDTO){

        Optional<UsuarioEntity> optionalUsuario = usuarioRepository.findById(id);

        if (optionalUsuario.isPresent()) {

            UsuarioEntity usuario = optionalUsuario.get();
            usuario.actualizar(actualizarDTO);

            var hashpass = BCrypt.hashpw(usuario.getPassword(), BCrypt.gensalt());

            usuario.setClave(hashpass);

            usuarioRepository.save(usuario);

            return new ResponseEntity<>("El usuario se actualizo correctamente", HttpStatus.OK);
        }

        return new ResponseEntity<>("El usuario no se encontro", HttpStatus.NOT_FOUND);
    }

    @PutMapping("/topico-{id}-actualizar")
    @Transactional
    public ResponseEntity<String> actualizarTopico(@PathVariable Long id,
                                                             @RequestBody TopicoActualizarDTO topicoActualizarDTO){
        Optional<TopicoEntity> optionalTopico = topicoRepository.findById(id);

        if (optionalTopico.isPresent()) {

            TopicoEntity topicoEntity = optionalTopico.get();
            topicoEntity.actualizar(topicoActualizarDTO);

            LocalDate localDate = LocalDate.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

            topicoEntity.setFecha(localDate.format(formatter));

            topicoRepository.save(topicoEntity);

            return new ResponseEntity<>("El topico se actualizo correctamente", HttpStatus.OK);
        }

        return new ResponseEntity<>("El topico no se encontro", HttpStatus.NOT_FOUND);
    }

    @PutMapping("/respuestas-{id}-actualizar")
    @Transactional
    public ResponseEntity<String> actualizarRespuesta(@PathVariable Long id,
                                                                @RequestBody RespuestasActualizarDTO respuestasActualizarDTO){
        Optional<RespuestasEntity> optionalRespuesta = respuestaRepository.findById(id);

        if (optionalRespuesta.isPresent()) {
            RespuestasEntity respuestasEntity = optionalRespuesta.get();
            respuestasEntity.actualizar(respuestasActualizarDTO);

            LocalDate localDate = LocalDate.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

            respuestasEntity.setFecha(localDate.format(formatter));

            respuestaRepository.save(optionalRespuesta.get());

            return new ResponseEntity<>("La respuesta se actualizo correctamente", HttpStatus.OK);
        }

        return new ResponseEntity<>("La respuesta no se encontro", HttpStatus.NOT_FOUND);
    }
}
