package com.auth.kavish;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("com.auth.kavish.repository")
public class KavishApplication {

	public static void main(String[] args) {
		SpringApplication.run(KavishApplication.class, args);
	}

}
