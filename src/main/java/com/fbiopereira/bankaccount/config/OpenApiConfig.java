package com.fbiopereira.bankaccount.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI filialOpenAPI() {
        return new OpenAPI()
                .components(new Components())
                .info(new Info()
                        .title("Bank Account API")
                        .description("A simple API to create and do operations in a Bank Account")
                        .contact(new Contact().email("fabio.pereira@outlook.com"))
                        .version("1.0")
                );

    }
}
