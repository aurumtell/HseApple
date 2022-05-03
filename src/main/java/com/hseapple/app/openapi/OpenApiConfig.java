package com.hseapple.app.openapi;

import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI openApi() {
        return new OpenAPI()
                .info(new Info()
                        .title("HseApple API")
                        .description("«HSE Apple» – educational social network for a research seminar on iOS development")
                        .version("v1.0")
                        .contact(new Contact()
                                .name("Tasbauova Dayana")
                                .url("https://t.me/aurum_tell")
                                .email("datasbauova@edu.hse.ru"))
                );
    }
}