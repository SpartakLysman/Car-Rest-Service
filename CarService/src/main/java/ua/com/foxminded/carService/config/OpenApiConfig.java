package ua.com.foxminded.carService.config;

import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;

@Configuration
@OpenAPIDefinition(info = @Info(title = "Car-rest-service", version = "v1"), security = @SecurityRequirement(name = "bearerAuth"), servers = {
		@Server(description = "develop", url = "http://localhost:8080/car-rest-service/") })
@SecurityScheme(name = "bearerAuth", type = SecuritySchemeType.HTTP, bearerFormat = "JWT", scheme = "bearer", in = SecuritySchemeIn.HEADER)
public class OpenApiConfig {

}