package de.amit.battlequest.controller.login;

import de.amit.battlequest.model.Player;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")

public class LoginRessource {

	@Autowired
	private PlayerRepository playerRepository;

	/*
	@PostMapping
	public String getToken(@RequestBody Player player) {
		System.out.println(player.getId());
		System.out.println(player.getPassword());
		return player.getId();
	}
	*/

	//

	@PostMapping
	public void createUser(@RequestBody String username){
		System.out.println("Creating user: " + username);
		Player p = playerRepository.findByUsername(username);
		System.out.println("test");
		if (p == null) {
			Player ply = new Player();
			ply.setUsername(username);
			ply.setPoints(0);
			playerRepository.save(ply);
			System.out.println("success");
			System.out.println("User ID: " + ply.getId());
		}
		else {
			System.out.println("already exists");
		}
	}
	@PostMapping("/{id}")
	public void setPassword(@PathVariable Long id, @RequestBody String password) {
		Player p = playerRepository.findById(id).orElse(null);
		System.out.println("Changing password of: " + p.getUsername());
		if(p != null){
			p.setPassword(password);
			playerRepository.save(p);
			System.out.println("success");
		}
		else {
			System.out.println("error");
		}
	}
	@PutMapping("/{id}")
	public void modifyUsername(@PathVariable Long id, @RequestBody String newUsername) {
		Player player = playerRepository.findById(id).orElse(null);
		System.out.println("Changing username of: " + player.getUsername());
		if(player != null){
			player.setUsername(newUsername);
			System.out.println("New username: " + player.getUsername());
			playerRepository.save(player);
			System.out.println("success");
		}
		else {
			System.out.println("doesn't exist");
		}
	}
	@GetMapping("/{id}")
	public Player getUser(@PathVariable Long id){
		return playerRepository.findById(id).orElse(null);
	}
	@DeleteMapping("/{id}")
	public void deleteUser(@PathVariable Long id) {
		Player player = playerRepository.findById(id).orElse(null);
		System.out.println("Deleting: " + player.getUsername());
		if(player != null){
			playerRepository.delete(player);
			System.out.println("success");
		}
		else {
			System.out.println("doesn't exist");
		}
	}
}
