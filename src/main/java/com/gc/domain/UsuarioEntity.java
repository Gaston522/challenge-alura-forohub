package com.gc.domain;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "usuario")
public class UsuarioEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String clave;
    private String curso;
    @OneToMany(mappedBy = "usuario")
    private List<TopicoEntity> topicos;

    public UsuarioEntity() {
    }

    public UsuarioEntity(UsuarioDTO usuarioDTO) {
        this.nombre = usuarioDTO.nombre();
        this.clave = usuarioDTO.clave();
        this.curso = usuarioDTO.curso();
        this.topicos = usuarioDTO.topicos();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }

    public List<TopicoEntity> getTopicos() {
        return topicos;
    }

    public void setTopicos(List<TopicoEntity> topicos) {
        this.topicos = topicos;
    }

    public void agregarTopico(TopicoEntity t){
        this.topicos.add(t);
    }
}
