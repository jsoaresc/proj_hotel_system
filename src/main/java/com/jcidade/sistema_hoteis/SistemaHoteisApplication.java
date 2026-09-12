package com.jcidade.sistema_hoteis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class SistemaHoteisApplication {
	public static void main(String[] args) {
		SpringApplication.run(SistemaHoteisApplication.class, args);
	}
}