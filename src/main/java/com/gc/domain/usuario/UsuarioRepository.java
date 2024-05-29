package com.gc.domain.usuario;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<UsuarioEntity, Long> {
    UsuarioEntity findByNombre(String nombre);
}
