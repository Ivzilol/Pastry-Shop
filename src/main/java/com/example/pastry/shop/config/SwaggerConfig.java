package com.example.pastry.shop.config;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@SecurityScheme(
        name = "Authorization",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "bearer"
)
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI().info(
                new Info().
                        title("Pastry Shop").
                        contact(
                                new Contact().
                                        email("ivailoali@gmail.com")
                                        .name("Ivaylo Alichkov")

                        ).
                        description("Welcome to Mom's Sweet Shop, an app that simulates online " +
                                "ordering and delivery of confectionery.")
        );
    }
}
