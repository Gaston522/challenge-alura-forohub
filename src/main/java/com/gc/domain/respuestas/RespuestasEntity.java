package com.gc.domain.respuestas;

import com.gc.domain.topico.TopicoActualizarDTO;
import com.gc.domain.topico.TopicoEntity;
import com.gc.domain.usuario.UsuarioEntity;
import jakarta.persistence.*;

@Entity
@Table(name = "respuesta")
public class RespuestasEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String titulo;
    String respuesta;
    String fecha;
    @ManyToOne
    @JoinColumn(name = "user_id")
    UsuarioEntity usuario;
    @ManyToOne
    TopicoEntity topico;

    public RespuestasEntity() {
    }

    public RespuestasEntity(RespuestaDTO respuestaDTO) {
        this.titulo = respuestaDTO.titulo();
        this.respuesta = respuestaDTO.respuesta();
        this.fecha = respuestaDTO.fecha();
        this.usuario = respuestaDTO.usuario();
        this.topico = respuestaDTO.topico();
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

    public String getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(String respuesta) {
        this.respuesta = respuesta;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public UsuarioEntity getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioEntity usuario) {
        this.usuario = usuario;
    }

    public TopicoEntity getTopico() {
        return topico;
    }

    public void setTopico(TopicoEntity topico) {
        this.topico = topico;
    }

    public void actualizar(RespuestasActualizarDTO rADto){
        if(rADto.titulo() != null) this.setTitulo(rADto.titulo());
        if(rADto.respuesta() != null) this.setRespuesta(rADto.respuesta());
    }
}
