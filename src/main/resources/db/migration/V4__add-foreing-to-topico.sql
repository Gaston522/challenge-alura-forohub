ALTER TABLE topico ADD usuario_id bigint;
ALTER TABLE topico ADD CONSTRAINT fk_usuario_id FOREIGN KEY (usuario_id) REFERENCES usuario(id);