package com.tsp.foxnight;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class FoxnightApplication {

	public static void main(String[] args) {
		SpringApplication.run(FoxnightApplication.class, args);
	}

}
