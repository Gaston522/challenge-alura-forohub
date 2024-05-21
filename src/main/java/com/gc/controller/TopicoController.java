package com.gc.controller;

import com.gc.domain.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/foro")
public class TopicoController {

    @Autowired
    private TopicoRepository topicoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    private UsuarioEntity usuarioEntity;

    @PostMapping
    @Transactional
    public ResponseEntity<String> login(@RequestBody @Valid UsuarioDTO usuarioDTO){

        usuarioEntity = new UsuarioEntity(usuarioDTO);
        usuarioRepository.save(usuarioEntity);

        return new ResponseEntity<>("Ingreso con éxito", HttpStatus.OK);
    }

    @PostMapping("/topico")
    @Transactional
    public ResponseEntity<String> registrarTopico(@RequestBody @Valid RegistroTopicoDTO registroTopicoDTO){

        LocalDate localDate = LocalDate.now();
        TopicoEntity topicoEntity = new TopicoEntity(registroTopicoDTO);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        topicoEntity.setFecha(localDate.format(formatter));

        topicoRepository.save(topicoEntity);

        return new ResponseEntity<>("Tópico creado con éxito", HttpStatus.OK);
    }
}
