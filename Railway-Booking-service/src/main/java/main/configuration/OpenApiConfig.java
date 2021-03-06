package main.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@Configuration
public class OpenApiConfig {
	
	@Bean
	public OpenAPI openAPI() {
		return new OpenAPI().info(new Info()
				             .title("BOOKING-MICROSERVICE")
				             .description("Booking a train ticket or cancel a ticket")
				             .version("v1.0")
				             .contact(new Contact()
				            		 .name("LokeshChary")
				            		 .email("lokeshchary0901@gmail.com"))
				             .termsOfService("TOC")
				             .license(new License().name("License").url("#")));
	}
}