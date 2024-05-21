package com.gc.domain;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "topico")
public class TopicoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titulo;
    private String mensaje;
    private String fecha;
    private String estatus;
    @ManyToOne
    private UsuarioEntity usuario;

    public TopicoEntity() {
    }

    public TopicoEntity(RegistroTopicoDTO rtDto) {
        this.titulo = rtDto.titulo();
        this.mensaje = rtDto.mensaje();
        this.fecha = rtDto.fecha();
        this.estatus = rtDto.estatus();
        this.usuario = rtDto.autor();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getEstatus() {
        return estatus;
    }

    public void setEstatus(String estatus) {
        this.estatus = estatus;
    }

    public UsuarioEntity getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioEntity usuario) {
        this.usuario = usuario;
    }
}
