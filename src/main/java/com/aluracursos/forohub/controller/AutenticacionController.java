package com.aluracursos.forohub.controller;

import com.aluracursos.forohub.domain.usuario.DatosLoginUsuario;
import com.aluracursos.forohub.domain.usuario.Usuario;
import com.aluracursos.forohub.infra.security.DatosTokenAcceso;
import com.aluracursos.forohub.infra.security.TokenService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
@RequiredArgsConstructor
public class AutenticacionController {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    @PostMapping
    public ResponseEntity<DatosTokenAcceso> iniciarSesion(@RequestBody @Valid DatosLoginUsuario datos) {

        var tokenAutenticacion = new UsernamePasswordAuthenticationToken(
                datos.login(),
                datos.clave()
        );

        var autenticacion = authenticationManager.authenticate(tokenAutenticacion);
        var usuarioAutenticado = (Usuario) autenticacion.getPrincipal();
        var jwt = tokenService.generarToken(usuarioAutenticado);

        return ResponseEntity.ok(new DatosTokenAcceso(jwt));
    }
}