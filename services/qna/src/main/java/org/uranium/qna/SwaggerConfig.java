package org.uranium.qna;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public GroupedOpenApi api() {
        return GroupedOpenApi.builder()
                .group("Q&A APIs")
                .pathsToMatch("/**")
                .build();
    }

    @Bean
    public OpenAPI apiInfo() {
        return new OpenAPI()
                .info(new Info().title("Q&A API")
                        .description("Processors questions & answers")
                        .version("v0.0.1")
                        .license(new License().name("Apache 2.0").url("http://uranium.org")))
                .externalDocs(new ExternalDocumentation()
                        .description("Uranium Wiki Documentation")
                        .url("https://uranium.wiki.github.org/docs"));
    }
}
