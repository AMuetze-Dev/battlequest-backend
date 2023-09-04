package de.amit.battlequest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

import de.amit.battlequest.controller.config.CorsConfig;

@SpringBootApplication
@ComponentScan(basePackages = "de.amit.battlequest.controller")
@Import(CorsConfig.class)
public class BattlequestApplication {

	public static void main(String[] args) {
		SpringApplication.run(BattlequestApplication.class, args);
	}

}
