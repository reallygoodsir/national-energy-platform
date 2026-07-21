package com.really.good.sir.energy.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI nationalEnergyPlatformOpenApi() {
        return new OpenAPI()
                .info(new Info()
                        .title("National Energy Platform API")
                        .description("Endpoints for managing electric meters, apartments, and consumer meter readings")
                        .version("1.0"));
    }
}