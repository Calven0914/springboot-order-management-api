package com.example.ordermanagement.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI orderManagementOpenApi() {
        return new OpenAPI()
                .info(new Info()
                        .title("Order Management API")
                        .description("Interview-ready Spring Boot REST API for customers, products, and orders.")
                        .version("v1")
                        .contact(new Contact()
                                .name("Calven")
                                .email("calvenc0914@gmail.com"))
                        .license(new License()
                                .name("Demo Project")));
    }
}
