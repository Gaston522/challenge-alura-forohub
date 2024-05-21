package com.gc.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class Servicio {

    @Autowired
    UsuarioRepository usuarioRepository;
    @Autowired
    TopicoRepository topicoRepository;

    @Transactional
    public void guardar(){

        UsuarioEntity user = usuarioRepository.findById(1L).get();

        TopicoEntity topico = new TopicoEntity();
        topico.setTitulo("Nuevo 2");
        topico.setMensaje("mensaje 2");
        topico.setFecha("10-05-2024");
        topico.setEstatus("activo");
        topico.setUsuario(user);

        user.agregarTopico(topico);

        usuarioRepository.save(user);
        topicoRepository.save(topico);
    }
}
