package com.ureca.sole_paradise;

import com.ureca.sole_paradise.util.NaverApiToDatabase;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class SoleParadiseApplication {

	public static void main(String[] args) {
		SpringApplication.run(SoleParadiseApplication.class, args);
	}

	/*
	@Bean
	public CommandLineRunner run(NaverApiToDatabase naverApiToDatabase) {
		return args -> {
			naverApiToDatabase.fetchDataAndSave();
		};
	}
	 */
}
