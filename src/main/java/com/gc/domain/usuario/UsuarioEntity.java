package com.gc.domain.usuario;

import com.gc.domain.respuestas.RespuestasEntity;
import com.gc.domain.topico.TopicoEntity;
import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "usuario")
public class UsuarioEntity implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String clave;
    private String curso;
    @OneToMany(mappedBy = "usuario")
    private List<TopicoEntity> topicos;
    @OneToMany(mappedBy = "usuario")
    private List<RespuestasEntity> respuestas;

    public UsuarioEntity() {
    }

    public UsuarioEntity(UsuarioDTO usuarioDTO) {
        this.nombre = usuarioDTO.nombre();
        this.clave = usuarioDTO.clave();
        this.curso = usuarioDTO.curso();
        this.topicos = usuarioDTO.topicos();
        this.respuestas = usuarioDTO.respuestas();
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

    public List<RespuestasEntity> getRespuestas() {
        return respuestas;
    }

    public void setRespuestas(List<RespuestasEntity> respuestas) {
        this.respuestas = respuestas;
    }

    public void agregarRespuesta(RespuestasEntity r){
        this.respuestas.add(r);
    }

    public void actualizar(UsuarioActualizarDTO actualizarDTO){
        if(actualizarDTO.nombre() != null && !actualizarDTO.nombre().equals("")) this.setNombre(actualizarDTO.nombre());
        if(actualizarDTO.clave() != null && !actualizarDTO.clave().equals("")) this.setClave(actualizarDTO.clave());
        if(actualizarDTO.curso() != null && !actualizarDTO.curso().equals("")) this.setCurso(actualizarDTO.curso());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getPassword() {
        return clave;
    }

    @Override
    public String getUsername() {
        return nombre;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
