package uz.pdp.student_service.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "STUDENT SERVICE",
                version = "2.6.0",
                description = "STUDENTLAR HAQIDA DOCUMENTATION"),
        servers = @Server(url = "http://localhost:1134", description = "Local Server")
)
public class SwaggerConfig {

}