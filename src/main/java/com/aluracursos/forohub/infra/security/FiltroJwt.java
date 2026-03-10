package com.aluracursos.forohub.infra.security;

import com.aluracursos.forohub.domain.usuario.UsuarioRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class FiltroJwt extends OncePerRequestFilter {

    private final TokenService tokenService;
    private final UsuarioRepository usuarioRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String encabezado = request.getHeader("Authorization");

        if (encabezado != null && encabezado.startsWith("Bearer ")) {
            String token = encabezado.replace("Bearer ", "").trim();
            String loginUsuario = tokenService.obtenerSubject(token);

            if (loginUsuario != null && SecurityContextHolder.getContext().getAuthentication() == null) {

                var usuarioOptional = usuarioRepository.findUsuarioByLogin(loginUsuario);

                if (usuarioOptional.isPresent()) {
                    var usuario = usuarioOptional.get();

                    var autenticacion = new UsernamePasswordAuthenticationToken(
                            usuario,
                            null,
                            usuario.getAuthorities()
                    );

                    SecurityContextHolder.getContext().setAuthentication(autenticacion);
                }
            }
        }

        filterChain.doFilter(request, response);
    }
}