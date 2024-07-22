package org.uranium.payments;

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
                .group("Payments APIs")
                .pathsToMatch("/**")
                .build();
    }

    @Bean
    public OpenAPI apiInfo() {
        return new OpenAPI()
                .info(new Info().title("Payments API")
                        .description("Records payments made by each tenant in apartment")
                        .version("v0.0.1")
                        .license(new License().name("Apache 2.0").url("http://uranium.org")))
                .externalDocs(new ExternalDocumentation()
                        .description("Uranium Wiki Documentation")
                        .url("https://uranium.wiki.github.org/docs"));
    }
}
