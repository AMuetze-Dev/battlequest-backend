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
	public void createUser(@RequestBody Player player){
		Player p = playerRepository.findById(player.getId()).orElse(null);
		if (p == null) {
			playerRepository.save(player);
			System.out.println("success");
		}
		else {
			System.out.println("already exists");
		}
	}
	@PutMapping("/{id}")
	public void modifyUsername(@PathVariable String id, @RequestBody String newId) {
		Player player = playerRepository.findById(id).orElse(null);
		if(player != null){
			player.setId(newId);
			playerRepository.save(player);
			System.out.println("success");
		}
		else {
			System.out.println("doesn't exist");
		}
	}
	@GetMapping("/{id}")
	public Player getUser(@PathVariable String id){
		return playerRepository.findById(id).orElse(null);
	}
	@DeleteMapping("/{id}")
	public void deleteUser(@PathVariable String id) {
		Player player = playerRepository.findById(id).orElse(null);
		if(player != null){
			playerRepository.delete(player);
			System.out.println("success");
		}
		else {
			System.out.println("doesn't exist");
		}
	}
}
