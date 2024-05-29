package com.gc.domain.topico;

import com.gc.domain.respuestas.RespuestasEntity;
import com.gc.domain.usuario.UsuarioEntity;
import jakarta.persistence.*;

import java.util.List;

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
    @OneToMany(mappedBy = "topico")
    private List<RespuestasEntity> respuestas;

    public TopicoEntity() {
    }

    public TopicoEntity(TopicoDTO tDto) {
        this.titulo = tDto.titulo();
        this.mensaje = tDto.mensaje();
        this.fecha = tDto.fecha();
        this.estatus = tDto.estatus();
        this.usuario = tDto.usuario();
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

    public List<RespuestasEntity> getRespuestas() {
        return respuestas;
    }

    public void setRespuestas(List<RespuestasEntity> respuestas) {
        this.respuestas = respuestas;
    }

    public void actualizar(TopicoActualizarDTO topicoActualizarDTO){
        if(topicoActualizarDTO.titulo() != null) this.setTitulo(topicoActualizarDTO.titulo());
        if(topicoActualizarDTO.mensaje() != null) this.setMensaje(topicoActualizarDTO.mensaje());
        if(topicoActualizarDTO.estatus() != null) this.setEstatus(topicoActualizarDTO.estatus());
    }

    public void agregarRespuesta(RespuestasEntity r){
        this.respuestas.add(r);
    }
}
