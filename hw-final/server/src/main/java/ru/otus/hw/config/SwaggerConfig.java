package ru.otus.hw.config;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    // http://localhost:8080/swagger-ui/index.html
    // http://localhost:8080/swagger-ui

    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("LotusApplication")
                .pathsToMatch("/**")
                .build();
    }

}
