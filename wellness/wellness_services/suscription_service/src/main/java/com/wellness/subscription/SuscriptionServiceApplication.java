package com.wellness.subscription;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

@SpringBootApplication
@EnableDiscoveryClient
@OpenAPIDefinition(info = @Info(title = "WELLNESS SUSCRIPTION DETAILS API", version = "1.0.0", description = "Wellness subscription related Api's are provided in this document", contact = @Contact(name = "Mr BALA KRISHNA", email = "balakrishna.jonnadula@ojas-it.com")))
public class SuscriptionServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SuscriptionServiceApplication.class, args);
	}

	
}
