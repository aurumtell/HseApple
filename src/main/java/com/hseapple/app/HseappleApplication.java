package com.hseapple.app;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;



@SpringBootApplication
@Configuration
@EnableJpaRepositories("com.hseapple")
@ComponentScan("com.hseapple")
@EntityScan("com.hseapple")
@SecurityScheme(name = "Authorization", scheme = "apiKey", type = SecuritySchemeType.APIKEY, in = SecuritySchemeIn.HEADER)
public class HseappleApplication {
	public static void main(String[] args) {
		SpringApplication.run(HseappleApplication.class, args);
	}

//	@Bean
//	public Docket productApi() {
//		return new Docket(DocumentationType.SWAGGER_2)
//				.select().apis(RequestHandlerSelectors.basePackage("com.hseapple"))
//				.build();
//	}
}
