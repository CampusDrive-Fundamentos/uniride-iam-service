package com.campusdrive.uniride_iam_service.infrastructure.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {

        @Value("${swagger.gateway-url:http://localhost:8080}")
        private String gatewayUrl;

        @Bean
        public OpenAPI openApiConfig() {
                Server gatewayServer = new Server()
                                .url(gatewayUrl)
                                .description("API Gateway");

                return new OpenAPI()
                                .servers(List.of(gatewayServer))
                                .info(new Info()
                                                .title("UniRide IAM Service API")
                                                .description("Identity and Access Management for UniRide Platform")
                                                .version("v1.0"))
                                .addSecurityItem(new SecurityRequirement().addList("Bearer Authentication"))
                                .components(new Components()
                                                .addSecuritySchemes("Bearer Authentication", createSecurityScheme()));
        }

        private SecurityScheme createSecurityScheme() {
                return new SecurityScheme()
                                .name("Bearer Authentication")
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT");
        }
}
