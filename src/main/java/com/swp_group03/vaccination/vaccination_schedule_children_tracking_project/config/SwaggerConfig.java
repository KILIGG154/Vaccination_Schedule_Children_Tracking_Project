package com.swp_group03.vaccination.vaccination_schedule_children_tracking_project.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.*;
//import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.Components;

@Configuration
@OpenAPIDefinition(
        info = @Info(title = "Vaccination API", version = "1.0", description = "API for Vaccination System"),
        security = @SecurityRequirement(name = "BearerAuth") // Yêu cầu xác thực bằng JWT
)
@SecurityScheme(
        name = "BearerAuth",
        type = SecuritySchemeType.HTTP,
        scheme = "bearer",
        bearerFormat = "JWT" // Xác định định dạng token
)
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .addSecurityItem(new io.swagger.v3.oas.models.security.SecurityRequirement().addList("BearerAuth")) // Thêm JWT vào Swagger UI
                .components(new Components().addSecuritySchemes("BearerAuth",
                        new io.swagger.v3.oas.models.security.SecurityScheme()
                                .name("BearerAuth")
                                .type(io.swagger.v3.oas.models.security.SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")));
    }
}
