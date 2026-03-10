package com.aluracursos.forohub.domain.topico;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TopicoRepository extends JpaRepository<Topico, Long> {

    boolean existsByTituloAndMensaje(String titulo, String mensaje);

    Page<Topico> findAllByActivoTrue(Pageable paginacion);

    Optional<Topico> findByIdAndActivoTrue(Long id);
}