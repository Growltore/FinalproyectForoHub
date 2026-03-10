package com.aluracursos.forohub.controller;

import com.aluracursos.forohub.domain.topico.*;
import com.aluracursos.forohub.domain.usuario.UsuarioRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/topicos")
@RequiredArgsConstructor
public class TopicoController {

    private final TopicoRepository topicoRepository;
    private final UsuarioRepository usuarioRepository;

    @PostMapping
    @Transactional
    public ResponseEntity<?> registrar(@RequestBody @Valid DatosRegistroTopico datos,
                                       UriComponentsBuilder uriBuilder,
                                       org.springframework.security.core.Authentication authentication) {

        if (topicoRepository.existsByTituloAndMensaje(datos.titulo(), datos.mensaje())) {
            return ResponseEntity.badRequest().body("Ya existe un tópico con el mismo título y mensaje");
        }

        var usuario = usuarioRepository.findUsuarioByLogin(authentication.getName())
                .orElseThrow(() -> new RuntimeException("Usuario autenticado no encontrado"));

        var topico = new Topico(datos, usuario);
        topicoRepository.save(topico);

        var uri = uriBuilder.path("/topicos/{id}")
                .buildAndExpand(topico.getId())
                .toUri();

        return ResponseEntity.created(uri).body(new DatosDetalleTopico(topico));
    }

    @GetMapping
    public ResponseEntity<Page<DatosListaTopico>> listar(
            @PageableDefault(size = 10) Pageable paginacion) {

        var pagina = topicoRepository.findAllByActivoTrue(paginacion)
                .map(DatosListaTopico::new);

        return ResponseEntity.ok(pagina);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> detallar(@PathVariable Long id) {
        var topico = topicoRepository.findByIdAndActivoTrue(id);

        if (topico.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(new DatosDetalleTopico(topico.get()));
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<?> actualizar(@PathVariable Long id,
                                        @RequestBody @Valid DatosActualizacionTopico datos,
                                        org.springframework.security.core.Authentication authentication) {

        var topicoOptional = topicoRepository.findByIdAndActivoTrue(id);

        if (topicoOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        var topico = topicoOptional.get();

        if (!topico.getAutor().getLogin().equals(authentication.getName())) {
            return ResponseEntity.status(403).body("No tienes permiso para actualizar este tópico");
        }

        topico.actualizarDatos(datos);

        return ResponseEntity.ok(new DatosDetalleTopico(topico));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<?> eliminar(@PathVariable Long id,
                                      org.springframework.security.core.Authentication authentication) {

        var topicoOptional = topicoRepository.findByIdAndActivoTrue(id);

        if (topicoOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        var topico = topicoOptional.get();

        if (!topico.getAutor().getLogin().equals(authentication.getName())) {
            return ResponseEntity.status(403).body("No tienes permiso para eliminar este tópico");
        }

        topico.eliminarLogicamente();

        return ResponseEntity.noContent().build();
    }
}