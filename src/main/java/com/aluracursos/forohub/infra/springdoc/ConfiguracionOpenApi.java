package com.aluracursos.forohub.infra.springdoc;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfiguracionOpenApi {

    private static final String ESQUEMA_SEGURIDAD = "bearer-key";

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("ForoHub API")
                        .version("1.0")
                        .description("API REST para gestión de usuarios y tópicos")
                        .contact(new Contact().name("Cristian Figueroa")))
                .addSecurityItem(new SecurityRequirement().addList(ESQUEMA_SEGURIDAD))
                .components(new Components()
                        .addSecuritySchemes(ESQUEMA_SEGURIDAD,
                                new SecurityScheme()
                                        .name(ESQUEMA_SEGURIDAD)
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")));
    }
}