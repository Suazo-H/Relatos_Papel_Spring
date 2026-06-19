package com.unir.relatos.orders.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI ordersOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Pedidos API - Relatos de Papel")
                        .description("Microservicio de gestión de pedidos para la librería Relatos de Papel. " +
                                "Permite registrar compras validando disponibilidad y stock con el microservicio de catálogo " +
                                "mediante comunicación HTTP usando Eureka (sin IP ni puerto). " +
                                "También permite recuperar las órdenes recientes de un usuario.")
                        .version("1.0.0"))
                .servers(List.of(
                        new Server().url("http://localhost:8082").description("Acceso directo al microservicio"),
                        new Server().url("http://localhost:8080").description("Acceso a través del Gateway")
                ));
    }
}
