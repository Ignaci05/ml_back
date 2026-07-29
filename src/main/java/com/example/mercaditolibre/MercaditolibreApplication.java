package com.example.mercaditolibre;

import java.util.Map;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class MercaditolibreApplication {

	public static void main(String[] args) {
		SpringApplication.run(MercaditolibreApplication.class, args);
	}

	@GetMapping({"/", "/health"})
	public Map<String, String> healthCheck() {
		return Map.of("status", "UP", "message", "Mercadito Libre API running!");
	}
}
