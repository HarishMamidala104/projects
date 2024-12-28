package com.ojas.hiring.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;

@OpenAPIDefinition(
		info = @Info(contact = @Contact(name = "VINAY KUMAR ", email = "timepassusing22@gmail.com"), 
		description = "OpenApi Documentation for spring security", title = "OHAMS APPLICATION", version = "v2.0", license = @License(name = "Licence Name"), 
		termsOfService = "http://192.168.1.98:8084/hiring/swagger-ui/"

),

		security = { @SecurityRequirement(name = "bearerAuth") })
@SecurityScheme(name = "bearerAuth", description = "JWT auth description", type = SecuritySchemeType.HTTP, bearerFormat = "JWT", in = SecuritySchemeIn.HEADER, scheme = "bearer"

)
public class OpenApiConfig {

}
