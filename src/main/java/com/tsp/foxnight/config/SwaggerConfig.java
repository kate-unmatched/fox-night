package com.tsp.foxnight.config;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public GroupedOpenApi customApi() {
        return GroupedOpenApi.builder()
                .group("custom")
                .pathsToMatch("/**")
                .packagesToScan("com.tsp.foxnight.controllers")
                .build();
    }
}