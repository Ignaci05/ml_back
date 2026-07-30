package com.example.mercaditolibre;

import java.util.Map;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class MercaditolibreApplication {

	public static void main(String[] args) {
		SpringApplication.run(MercaditolibreApplication.class, args);
	}

	@Bean
	public CommandLineRunner alterImagenUrlColumn(JdbcTemplate jdbcTemplate) {
		return args -> {
			try {
				jdbcTemplate.execute("ALTER TABLE productos MODIFY COLUMN imagen_url TEXT");
				System.out.println("Columna imagen_url modificada a TEXT en MySQL.");
			} catch (Exception e) {
				System.out.println("Info sobre alter column: " + e.getMessage());
			}
		};
	}

	@GetMapping({"/", "/health"})
	public Map<String, String> healthCheck() {
		return Map.of("status", "UP", "message", "Mercadito Libre API running!");
	}
}
