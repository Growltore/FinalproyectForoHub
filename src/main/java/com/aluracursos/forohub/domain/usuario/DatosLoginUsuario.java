package com.aluracursos.forohub.domain.usuario;

import jakarta.validation.constraints.NotBlank;

public record DatosLoginUsuario(
        @NotBlank String login,
        @NotBlank String clave
) {
}