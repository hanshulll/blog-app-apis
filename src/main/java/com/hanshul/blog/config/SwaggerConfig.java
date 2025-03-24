package com.hanshul.blog.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "BlogSphere",
                version = "1.0",
                description = "API documentation for BlogSphere",
                termsOfService = "",
                contact = @Contact(name = "Support", email = ""),
                license = @License(name = "", url = "")
        )
)
public class SwaggerConfig {

}
