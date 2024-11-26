package com.propiaInmoviliaria.propia.config;

import com.propiaInmoviliaria.propia.security.AuthRequest;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.media.Schema;
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
                        .version("1.0")
                        .description("API documentation")
                );

    }
}
