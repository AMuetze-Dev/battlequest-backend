package de.amit.battlequest.controller.rest.player;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.amit.battlequest.model.Player;
import de.amit.battlequest.model.Response;

@RestController
@RequestMapping("/player/{uuid}/nickname")
public class NicknameRessource {

	@Autowired
	private PlayerRepository playerRepository;

	@Autowired
	private PlayerRessource playerRessource;

	@GetMapping
	public String readNickname(@PathVariable UUID uuid) {
		final Player player = playerRessource.read(uuid);
		return player != null ? player.getNickname() : null;
	}

	@PutMapping
	public Response updateNickname(@PathVariable UUID uuid, @RequestBody String nickname) {
		final Player player = playerRessource.read(uuid);
		if (player == null)
			return new Response(false, "Spieler existiert nicht");
		player.setNickname(nickname);
		playerRepository.save(player);
		return new Response(true, "Nutzername wurde angepasst");
	}
}
