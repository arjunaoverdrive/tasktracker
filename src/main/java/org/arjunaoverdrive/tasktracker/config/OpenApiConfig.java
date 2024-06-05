package org.arjunaoverdrive.tasktracker.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI openApiDescription() {
        Server localhostServer = new Server();
        localhostServer.setUrl("http://localhost:8080");
        localhostServer.setDescription("Local env");

        Contact contact = new Contact();
        contact.setEmail("igor.klimov.cc@gmail.com");

        License mitLicense = new License().name("GNU AGPLv3")
                .url("https://chooselicense.com/licenses/agpl-3.0");

        Info info = new Info()
                .title("Tasktracker API")
                .version("1.0")
                .contact(contact)
                .description("API for the Task tracker app")
                .license(mitLicense);

        return new OpenAPI().addSecurityItem(new SecurityRequirement().
                        addList("Basic Authentication"))
                .components(new Components().addSecuritySchemes
                        ("Basic Authentication", createAPIKeyScheme()))
                .info(info).servers(List.of(localhostServer));
    }

    private SecurityScheme createAPIKeyScheme() {
        return new SecurityScheme().type(SecurityScheme.Type.HTTP).name("basicAuth").scheme("basic");
    }

    @Bean
    public GroupedOpenApi tasksOpenApi(@Value("${springdoc.version}") String appVersion) {
        String[] paths = { "/api/v1/**" };
        return GroupedOpenApi.builder().
                group("entities")
                .addOpenApiCustomizer(openApi -> openApi.info(new Info().title("Tasks API").version(appVersion)))
                .pathsToMatch(paths)
                .build();
    }

    @Bean
    public GroupedOpenApi streamOpenApi(@Value("${springdoc.version}") String appVersion) {
        String[] paths = { "/api/v1/tasks/stream/**" };
        String[] packagedToMatch = { "org.arjunaoverdrive.tasktracker" };
        return GroupedOpenApi.builder().group("x-stream")
                .addOpenApiCustomizer(openApi -> openApi.info(new Info().title("Stream API").version(appVersion)))
                .pathsToMatch(paths).packagesToScan(packagedToMatch)
                .build();
    }
}
