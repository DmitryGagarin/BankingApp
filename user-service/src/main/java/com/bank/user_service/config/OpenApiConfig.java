package com.bank.user_service.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(
        info = @Info(
                title = "Banking App System",
                description = "User Service - registration, login", version = "1.0.0",
                contact = @Contact(
                        name = "Dmitrii Gagarin",
                        email = "gagriu@yandex.ru"
                )
        )
)

public class OpenApiConfig {
}
