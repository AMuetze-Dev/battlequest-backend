package de.amit.battlequest.controller.rest.player;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.amit.battlequest.model.Credentials;
import de.amit.battlequest.model.Player;
import de.amit.battlequest.model.Response;

@RestController
@RequestMapping("/player/password")
public class PasswordRessource {

	@Autowired
	private PlayerRepository playerRepository;

	@Autowired
	private PlayerRessource playerRessource;

	@PutMapping("/{uuid}")
	public Response changePassword(@PathVariable UUID uuid, @RequestBody Credentials credentials) {
		final Player player = playerRepository.findById(uuid).orElse(null);
		if (player == null)
			return new Response(false, "Der angegebene Spieler konnte nicht gefunden werden");
		if (!player.getPassword().equals(credentials.getPassword()))
			return new Response(false, "Das alte Passwort ist nicht korrekt");
		player.setPassword(credentials.getNewPassword());
		playerRepository.save(player);
		return new Response(true, "Das Passwort wurde erfolgreich geändert");
	}

	@GetMapping("/validate")
	public boolean validate(@RequestBody Credentials credentials) {
		final Player player = playerRessource.read(credentials.getUsername());
		if (player == null || !credentials.getPassword().equals(player.getPassword()))
			return false;
		return true;
	}
}
