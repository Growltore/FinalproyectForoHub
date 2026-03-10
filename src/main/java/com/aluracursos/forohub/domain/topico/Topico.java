package com.aluracursos.forohub.domain.topico;

import com.aluracursos.forohub.domain.usuario.Usuario;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Table(name = "topicos")
@Entity(name = "Topico")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Topico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;

    private String mensaje;

    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion;

    @Enumerated(EnumType.STRING)
    private EstadoTopico estado;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "autor_id")
    private Usuario autor;

    private Boolean activo;

    public Topico(DatosRegistroTopico datos, Usuario creador) {
        this.titulo = datos.titulo();
        this.mensaje = datos.mensaje();
        this.fechaCreacion = LocalDateTime.now();
        this.estado = EstadoTopico.ABIERTO;
        this.autor = creador;
        this.activo = true;
    }

    public void actualizarDatos(DatosActualizacionTopico datos) {
        if (datos.titulo() != null && !datos.titulo().isBlank()) {
            this.titulo = datos.titulo();
        }

        if (datos.mensaje() != null && !datos.mensaje().isBlank()) {
            this.mensaje = datos.mensaje();
        }
    }

    public void eliminarLogicamente() {
        this.activo = false;
    }
}