package de.amit.battlequest.controller.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.amit.battlequest.model.Credentials;
import de.amit.battlequest.model.Player;
import de.amit.battlequest.model.Response;

@RestController
@RequestMapping("/login")

public class PlayerRessource {
	@Autowired
	private PlayerRepository playerRepository;

	@GetMapping("/validate")
	public Response checkPassword(@RequestBody Credentials credentials) {
		final Player player = getUser(credentials.getUsername());
		if (player == null)
			return new Response("The requested username is invalid.", false);
		if (!credentials.getPassword().equals(player.getPassword()))
			return new Response("The stated password does not match the login data.", false);
		return new Response("Login was successful", true);
	}

	@PostMapping
	public Response createUser(@RequestBody Credentials credentials) {
		final Player p = getUser(credentials.getUsername());
		if (p == null) {
			playerRepository.save(credentials.createPlayer());
			return new Response("User " + credentials.getUsername() + " was created successfully.", true);
		}
		return new Response("User already exists", false);
	}

	@DeleteMapping("/{id}")
	public Response deleteUser(@PathVariable Long id) {
		final Player player = getUser(id);
		if (player != null) {
			playerRepository.delete(player);
			new Response(player.getUsername() + " was deleted successfully.", true);
		}
		return new Response("User does not exist", false);
	}

	public PlayerRepository getPlayerRepository() {
		return playerRepository;
	}

	@GetMapping("/{id}")
	public Player getUser(@PathVariable Long id) {
		return playerRepository.findById(id).orElse(null);
	}

	@GetMapping
	public Player getUser(String username) {
		return playerRepository.findByUsername(username);
	}

	@PutMapping("/{id}")
	public Response modifyUsername(@PathVariable Long id, @RequestBody String newUsername) {
		final Player player = getUser(id);
		if (player == null)
			return new Response("User does not exist", false);
		player.setUsername(newUsername);
		playerRepository.save(player);
		return new Response("Username of " + player.getUsername() + " was modified successfully.", true);
	}
}
