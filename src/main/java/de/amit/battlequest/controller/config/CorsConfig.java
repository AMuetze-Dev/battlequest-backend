package de.amit.battlequest.controller.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/ws/**").allowedOrigins("http://localhost:3000", "http://aaronmuetze.ddns.net:3000")
				.allowedMethods("GET", "POST", "PUT", "DELETE").allowCredentials(true);
		registry.addMapping("/**").allowedOriginPatterns("*").allowedMethods("GET", "POST", "PUT", "DELETE")
				.allowCredentials(true);
	}

}
