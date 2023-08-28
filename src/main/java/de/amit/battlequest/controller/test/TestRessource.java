package de.amit.battlequest.controller.test;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestRessource {

	@GetMapping
	public String getOne() {
		return "1";
	}

	@GetMapping("/two")
	public String getTwo() {
		return "2";
	}

	@PostMapping
	public String postOne() {
		return "3";
	}

	@PostMapping("/two")
	public String postTwo(@RequestBody String output) {
		System.out.println(output);
		return "4";
	}

	@PutMapping("/{id}")
	public String putOne(@PathVariable Long id) {
		return "" + id;
	}
}
