package de.amit.battlequest.controller.login;
import de.amit.battlequest.model.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("/login")

public class PlayerRessource {
	@Autowired
	private PlayerRepository playerRepository;
	@PostMapping
	public Response createUser(@RequestBody Credentials credentials){
		Player p = getUser(credentials.getUsername());
		if (p == null) {
			playerRepository.save(credentials.createPlayer());
			return new Response("User " + credentials.getUsername() + " was created successfully.", true);
		}
		return new Response("User already exists", false);
	}
	@PutMapping("/{id}")
	public Response modifyUsername(@PathVariable Long id, @RequestBody String newUsername) {
		Player player = getUser(id);
		if(player == null)
			return new Response("User does not exist", false);
		player.setUsername(newUsername);
		playerRepository.save(player);
		return new Response("Username of " + player.getUsername() + " was modified successfully.", true);
	}
	@GetMapping("/{id}")
	public Player getUser(@PathVariable Long id){
		return playerRepository.findById(id).orElse(null);
	}
	@GetMapping
	public Player getUser(String username){
		return playerRepository.findByUsername(username);
	}
	@GetMapping("/validate")
	public Response checkPassword(@RequestBody Credentials credentials){
		Player player = getUser(credentials.getUsername());
		if(player == null)
			return new Response("The requested username is invalid.", false);
		if(Objects.equals(credentials.getPassword(), player.getPassword()))
			return new Response("The stated password does not match the login data.", false);
		return new Response("Login was successful", true);
	}
	@DeleteMapping("/{id}")
	public Response deleteUser(@PathVariable Long id) {
		Player player = getUser(id);
		if(player != null){
			playerRepository.delete(player);
			new Response(player.getUsername() + " was deleted successfully.", true);
		}
		return new Response("User does not exist", false);
	}
	public PlayerRepository getPlayerRepository() {
		return playerRepository;
	}
}
