package com.senac.ApiEduPlataformSenac.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfiguration {

    @Bean
    public OpenAPI customOpenApi(){
        return new OpenAPI().info(
                new Info().title("EduPlatForm").version("1.0")
                        .description("API da plataforma EduPlatform para gerenciamento de cursos e treinamentos online.")
        );
    }

}