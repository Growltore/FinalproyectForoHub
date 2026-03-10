    CREATE TABLE topicos (
        id BIGINT NOT NULL AUTO_INCREMENT,
        titulo VARCHAR(200) NOT NULL,
        mensaje TEXT NOT NULL,
        fecha_creacion DATETIME NOT NULL,
        estado VARCHAR(50) NOT NULL,
        autor_id BIGINT NOT NULL,
        activo TINYINT NOT NULL,
        PRIMARY KEY (id),
        CONSTRAINT fk_topicos_autor
            FOREIGN KEY (autor_id)
            REFERENCES usuarios(id)
    );