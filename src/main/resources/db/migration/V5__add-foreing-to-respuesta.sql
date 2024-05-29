ALTER TABLE respuesta ADD user_id bigint;
ALTER TABLE respuesta ADD topico_id bigint;
ALTER TABLE respuesta ADD CONSTRAINT fk_user_id FOREIGN KEY (user_id) REFERENCES usuario(id);
ALTER TABLE respuesta ADD CONSTRAINT fk_topico_id FOREIGN KEY (topico_id) REFERENCES topico(id);