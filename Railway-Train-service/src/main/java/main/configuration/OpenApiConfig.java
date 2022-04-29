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
				             .title("TRAIN-MICROSERVICE")
				             .description("Add the trains,delete the trains,update the trains,see all the trains,trains between the stations")
				             .version("v1.0")
				             .contact(new Contact()
				            		 .name("LokeshChary")
				            		 .email("lokeshchary0901@gmail.com"))
				             .termsOfService("TOC")
				             .license(new License().name("License").url("#")));
	}
}