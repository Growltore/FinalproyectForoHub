package com.aluracursos.forohub.infra.security;

import com.aluracursos.forohub.domain.usuario.Usuario;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    @Value("${api.security.secret}")
    private String claveSecreta;

    public String generarToken(Usuario usuario) {
        try {
            Algorithm algoritmo = Algorithm.HMAC256(claveSecreta);

            return JWT.create()
                    .withIssuer("forohub-api")
                    .withSubject(usuario.getLogin())
                    .withExpiresAt(fechaExpiracion())
                    .sign(algoritmo);

        } catch (JWTCreationException e) {
            throw new RuntimeException("No fue posible generar el token JWT", e);
        }
    }

    public String obtenerSubject(String tokenJwt) {
        if (tokenJwt == null || tokenJwt.isBlank()) {
            return null;
        }

        try {
            Algorithm algoritmo = Algorithm.HMAC256(claveSecreta);

            return JWT.require(algoritmo)
                    .withIssuer("forohub-api")
                    .build()
                    .verify(tokenJwt)
                    .getSubject();

        } catch (JWTVerificationException e) {
            return null;
        }
    }

    private Instant fechaExpiracion() {
        return LocalDateTime.now()
                .plusHours(2)
                .toInstant(ZoneOffset.of("-05:00"));
    }
}