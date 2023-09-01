package de.amit.battlequest.controller.login;

import de.amit.battlequest.model.Team;
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

	@GetMapping("/password/{id}")
	public Response changePassword(@PathVariable Long id, @RequestBody String newPassword){
		//TODO: make sure they are validated first? @Aaron: where?
		Player player = playerRepository.findById(id).orElse(null);
		player.setPassword(newPassword);
		playerRepository.save(player);
		return new Response("The password was changed successfully.", true);
	}
	@GetMapping("/validate")
	public Response checkPassword(@RequestBody Credentials credentials) {
		final Player player = getUser(credentials.getUsername());
		if (player == null)
			return new Response("The requested username is invalid.", false);
		if (!credentials.getPassword().equals(player.getPassword()))
			return new Response("The stated password does not match the login data.", false);
		return new Response("Login was successful", true);
	}

	@PostMapping("/create")
	public Response createUser(@RequestBody Credentials credentials) {
		final Player p = getUser(credentials.getUsername());
		if (p == null) {
			playerRepository.save(credentials.createPlayer());
			return new Response("User " + credentials.getUsername() + " was created successfully.", true);
		}
		return new Response("User already exists", false);
	}

	@PutMapping("/decrease/{id}")
	public Response decreasePoints(@PathVariable Long id, @RequestBody int number){
		Player player = playerRepository.findById(id).orElse(null);
		if(player == null)
			return new Response("There is no such player.", false);
		if(number > player.getPoints()) {
			resetPoints(id);
			return new Response("Points were set to 0 because there weren't enough points left.", false);
		}
		player.setPoints(player.getPoints() - number);
		playerRepository.save(player);
		return new Response(number + " points were subtracted successfully.", true);
	}

	@DeleteMapping("/delete/{id}")
	public Response deleteUser(@PathVariable Long id) {
		final Player player = getUser(id);
		if (player != null) {
			playerRepository.delete(player);
			new Response(player.getUsername() + " was deleted successfully.", true);
		}
		return new Response("User does not exist", false);
	}

	//TODO: delete because there are no usages?
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

	@PutMapping("/increase/{id}")
	public Response increasePoints(@PathVariable Long id, @RequestBody int number){
		Player player = playerRepository.findById(id).orElse(null);
		if(player == null)
			return new Response("There is no such player.", false);
		player.setPoints(player.getPoints() + number);
		playerRepository.save(player);
		return new Response(number + " points were added successfully.", true);
	}

	@PutMapping("/rename/{id}")
	public Response modifyNickname(@PathVariable Long id, @RequestBody String newNickname) {
		final Player player = getUser(id);
		if (player == null)
			return new Response("User does not exist", false);
		player.setNickname(newNickname);
		playerRepository.save(player);
		return new Response("Username of " + player.getUsername() + " was modified successfully.", true);
	}

	@PutMapping("/reset")
	public Response resetPoints(Long id){
		Player player = playerRepository.findById(id).orElse(null);
		player.setPoints(0);
		playerRepository.save(player);
		return new Response("Points were reset successfully.", true);
	}

	@PutMapping("/team/{id}")
	public Response setTeam(@PathVariable Long id, @RequestBody Team team) {
		Player player = playerRepository.findById(id).orElse(null);
		if(player == null)
			return new Response("The requested player is not available.", false);
		player.setTeam(team);
		playerRepository.save(player);
		return new Response(player.getNickname() + " was successfully assigned to Team " + team.getName(), true);
	}
}
