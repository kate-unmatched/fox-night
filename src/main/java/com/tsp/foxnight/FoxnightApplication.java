package com.tsp.foxnight;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaAuditing
@EntityScan("com.tsp.foxnight.entity")
@EnableJpaRepositories("com.tsp.foxnight.repositories")
@SpringBootApplication
public class FoxnightApplication {

	public static void main(String[] args) {
		SpringApplication.run(FoxnightApplication.class, args);
	}

}
