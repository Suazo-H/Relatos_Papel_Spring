package com.unir.relatos.catalogue.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI catalogueOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Catálogo API - Relatos de Papel")
                        .description("Microservicio de catálogo de libros para la librería Relatos de Papel. " +
                                "Permite gestionar el inventario de libros con operaciones CRUD y búsqueda combinada por: " +
                                "título, autor, género, ISBN, año de publicación, valoración y visibilidad.")
                        .version("1.0.0"))
                .servers(List.of(
                        new Server().url("http://localhost:8081").description("Acceso directo al microservicio"),
                        new Server().url("http://localhost:8080").description("Acceso a través del Gateway")
                ));
    }
}
