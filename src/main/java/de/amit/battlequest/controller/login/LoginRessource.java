package de.amit.battlequest.controller.login;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.amit.battlequest.model.Credentials;

@RestController
@RequestMapping("/login")
public class LoginRessource {

	@PostMapping
	public String getToken(@RequestBody Credentials credentials) {
		System.out.println(credentials.getUsername());
		System.out.println(credentials.getPassword());
		return "test123";
	}

}
