create table topico(

    id bigint not null auto_increment,
    titulo varchar(100) not null,
    mensaje varchar(100) not null,
    fecha varchar(100) not null,
    estatus varchar(100) not null,
    usuario_id bigint,

    primary key(id),

    constraint fk_usuario_id foreign key(usuario_id) references usuario(id)

);