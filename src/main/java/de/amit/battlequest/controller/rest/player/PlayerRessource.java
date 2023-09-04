package de.amit.battlequest.controller.rest.player;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.amit.battlequest.model.Credentials;
import de.amit.battlequest.model.Player;
import de.amit.battlequest.model.Response;

@RestController
@RequestMapping("/player")
public class PlayerRessource {

	@Autowired
	private PlayerRepository playerRepository;

	@PostMapping
	public Response create(@RequestBody Credentials credentials) {
		if (read(credentials.getUsername()) != null)
			return new Response(false, "Spieler existiert bereits");
		playerRepository.save(credentials.createPlayer());
		return new Response(true, "Nutzer wurde angelegt");
	}

	@DeleteMapping("/{uuid}")
	public Response delete(@PathVariable UUID uuid) {
		final Player player = read(uuid);
		if (player == null)
			return new Response(false, "Spieler existiert nicht");
		playerRepository.delete(player);
		return new Response(true, "Spieler wurde entfernt");
	}

	public Player read(String username) {
		return playerRepository.findByUsername(username);
	}

	@GetMapping("/{uuid}")
	public Player read(@PathVariable UUID uuid) {
		return playerRepository.findById(uuid).orElse(null);
	}
}
