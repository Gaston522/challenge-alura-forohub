package com.gc.domain.respuestas;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RespuestaRepository extends JpaRepository<RespuestasEntity, Long> {
    void deleteByTopicoId(Long topicoId);
}
