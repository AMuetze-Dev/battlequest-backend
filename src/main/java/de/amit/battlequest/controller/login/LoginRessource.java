package de.amit.battlequest.controller.login;

import de.amit.battlequest.model.Player;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")

public class LoginRessource {

	@Autowired
	private PlayerRepository playerRepository;

	@PostMapping
	public String getToken(@RequestBody Player player) {		//how does one build the player? do i grab the username and pw and have an incomplete user?
		System.out.println(player.getId());
		System.out.println(player.getPassword());
		return player.getId();
	}
	@PutMapping("/{id}")
	public void modifyUsername(@PathVariable String id, @RequestBody String newId) {
		Player player = playerRepository.findById(id).orElse(null);
		if(player != null){
			player.setId(newId);
			playerRepository.save(player);
		}
	}
}
