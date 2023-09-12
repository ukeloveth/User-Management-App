package com.assessment.user.management.app;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@OpenAPIDefinition(
		info = @Info(
				title = "User Management App",
				version = "1.0.0-SNAPSHOT",
				contact = @Contact(
						url = "https://google.com",
						name = "Loveth",
						email = "l@gmail.com"
				),
				description = "This is a user management app"
		),
		servers = {
				@Server(url = "/api", description = "Default Server URL")
		}
//		security = {
//				@SecurityRequirement(
//						name = "sample",
//						scopes = {"sample", "anotherScope"}
//				),
//
//		}
)
@SpringBootApplication
public class UserManagementAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserManagementAppApplication.class, args);
	}

}
