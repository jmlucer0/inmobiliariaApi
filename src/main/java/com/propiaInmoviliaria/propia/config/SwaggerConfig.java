package com.propiaInmoviliaria.propia.config;

import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;


@OpenAPIDefinition
@Configuration
public class SwaggerConfig {

    @Bean
    public OpenApiCustomizer openApiCustomiser() {
        return openApi -> openApi
                .addSecurityItem(new SecurityRequirement().addList("BearerAuth"))
                .components(new io.swagger.v3.oas.models.Components()
                        .addSecuritySchemes("BearerAuth",
                                new SecurityScheme()
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")))
                .info(new io.swagger.v3.oas.models.info.Info()
                        .title("ImobiliariaApi")
                        .version("1.0.0")
                        .description("API REST para la gestión inmobiliaria, diseñada para organizar propiedades y clientes." +
                                " Permite buscar propiedades por dirección y estado (disponibles, en reparación, desocupadas, etc.) y consultar las propiedades asociadas a un cliente. " +
                                "Ideal para empresas que buscan optimizar la administración de su inventario inmobiliario.")
                );

    }
}
