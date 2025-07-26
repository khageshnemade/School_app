package space.khagesh.school.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info().title("School API")
                        .version("v1")
                        .description("Spring Boot JWT secured API"))
                .components(new Components()
                        .addSecuritySchemes("BearerAuth", 
                            new SecurityScheme()
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")))
                .addSecurityItem(new SecurityRequirement().addList("BearerAuth"))
                .addServersItem(new Server().url("http://school.khagesh.space")) 
                .addServersItem(new Server().url("http://localhost:8083")) 
                .addServersItem(new Server().url("https://school.khagesh.space")) 
                .addServersItem(new Server().url("wss://school.khagesh.space/ws")); 
    }
}
