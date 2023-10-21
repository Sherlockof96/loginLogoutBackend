package com.auth.kavish;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class KavishApplication {

	@GetMapping("/")
	public String home() {
		return ("Welcome to First Spring Boot Application");
	}

	public static void main(String[] args) {
		SpringApplication.run(KavishApplication.class, args);
	}

}
