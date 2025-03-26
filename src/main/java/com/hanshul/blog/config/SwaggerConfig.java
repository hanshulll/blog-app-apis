package com.hanshul.blog.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.service.contexts.SecurityContext;

import java.util.Arrays;
import java.util.List;

import static com.hanshul.blog.utility.Constant.AUTHORIZATION_HEADER;

@Configuration
@OpenAPIDefinition(info = @Info(title = "BlogSphere", version = "1.0", description = "API documentation for BlogSphere", termsOfService = "", contact = @Contact(name = "Support", email = ""), license = @License(name = "", url = "")), security = @SecurityRequirement(name = "JWT"))
public class SwaggerConfig {

    @Bean
    public SecurityScheme securityScheme() {
        return new SecurityScheme().type(SecurityScheme.Type.APIKEY).in(SecurityScheme.In.HEADER).name("Authorization")
                .description("Please enter you JWT token!");
    }

    // Define the SecurityReference with AuthorizationScopes
    private List<SecurityReference> securityReferences() {
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[]{
                new AuthorizationScope("global", "accessEverything") // You can define more scopes if needed
        };
        return Arrays.asList(new SecurityReference("JWT", authorizationScopes));
    }

    // Define the SecurityContext for applying security globally
    @Bean
    public List<SecurityContext> securityContexts() {
        return Arrays.asList(SecurityContext.builder().securityReferences(securityReferences()).build());
    }

    // Apply the security configuration to the Swagger UI
    @Bean
    public OpenAPI openAPI() {
        return new io.swagger.v3.oas.models.OpenAPI()
                .addSecurityItem(new io.swagger.v3.oas.models.security.SecurityRequirement().addList("JWT"))
                .components(new io.swagger.v3.oas.models.Components().addSecuritySchemes("JWT",
                        new SecurityScheme().type(SecurityScheme.Type.APIKEY).in(SecurityScheme.In.HEADER)
                                .name(AUTHORIZATION_HEADER).description("Please input your JWT token!")));
    }
}
