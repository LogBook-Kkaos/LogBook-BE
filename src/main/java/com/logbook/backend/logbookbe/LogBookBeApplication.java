package com.logbook.backend.logbookbe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class LogBookBeApplication {

	public static void main(String[] args) {
		SpringApplication.run(LogBookBeApplication.class, args);
	}

}
