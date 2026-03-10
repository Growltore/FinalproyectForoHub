package com.aluracursos.forohub.controller;

import com.aluracursos.forohub.domain.usuario.DatosDetalleUsuario;
import com.aluracursos.forohub.domain.usuario.DatosRegistroUsuario;
import com.aluracursos.forohub.domain.usuario.Usuario;
import com.aluracursos.forohub.domain.usuario.UsuarioRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    @PostMapping
    @Transactional
    public ResponseEntity<?> registrar(@RequestBody @Valid DatosRegistroUsuario datos,
                                       UriComponentsBuilder uriBuilder) {

        if (usuarioRepository.existsByLogin(datos.login())) {
            return ResponseEntity.badRequest().body("Ya existe un usuario con ese login");
        }

        if (usuarioRepository.existsByEmail(datos.correo())) {
            return ResponseEntity.badRequest().body("Ya existe un usuario con ese correo");
        }

        String claveCifrada = passwordEncoder.encode(datos.clave());
        Usuario usuario = new Usuario(datos, claveCifrada);

        usuarioRepository.save(usuario);

        var uri = uriBuilder.path("/usuarios/{id}")
                .buildAndExpand(usuario.getId())
                .toUri();

        return ResponseEntity.created(uri).body(new DatosDetalleUsuario(usuario));
    }
}