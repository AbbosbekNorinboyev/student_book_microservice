package uz.pdp.book_service.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "BOOK SERVICE",
                version = "2.6.0",
                description = "BOOKS HAQIDA DOCUMENTATION"),
        servers = @Server(url = "http://localhost:1135", description = "Local Server")
)
public class SwaggerConfig {

}