package com.gc.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

/*@Service
public class RegistrarTopico {

    @Autowired
    private TopicoRepository topicoRepository;

    public void registrar(){

        LocalDate fecha = LocalDate.now();
        RegistroTopicoDTO registroTopicoDTO = new RegistroTopicoDTO("Prueba",
                "mensaje de prueba", fecha, "activo", "alura");

        topicoRepository.save(new TopicoEntity(registroTopicoDTO));
    }
}*/
