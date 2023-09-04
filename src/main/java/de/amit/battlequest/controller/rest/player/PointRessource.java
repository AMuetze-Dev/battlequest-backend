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
@RequestMapping("/player/{uuid}/points")
public class PointRessource {

	@Autowired
	private PlayerRepository playerRepository;

	@PutMapping("/decrease")
	public Response decrease(@PathVariable UUID uuid) {
		return update(uuid, -1);
	}

	@GetMapping
	public int get(@PathVariable UUID uuid) {
		final Player player = playerRepository.findById(uuid).orElse(null);
		return player == null ? -1 : player.getPoints();
	}

	@PutMapping("/increase")
	public Response increase(@PathVariable UUID uuid) {
		return update(uuid, 1);
	}

	@PutMapping("/reset")
	public Response reset(@PathVariable UUID uuid) {
		final Player player = playerRepository.findById(uuid).orElse(null);
		if (player == null)
			return new Response(false, "Der angegebene Spieler konnte nicht gefunden werden");
		player.setPoints(0);
		playerRepository.save(player);
		return new Response(true, "Die Punkte wurden erfolgreich zurückgesetzt");
	}

	@PutMapping("/set")
	public Response set(@PathVariable UUID uuid, @RequestBody int amount) {
		final Player player = playerRepository.findById(uuid).orElse(null);
		if (player == null)
			return new Response(false, "Der angegebene Spieler konnte nicht gefunden werden");
		player.setPoints(amount);
		playerRepository.save(player);
		return new Response(true, "Die Punkte wurden erfolgreich gesetzt");
	}

	private Response update(UUID uuid, int delta) {
		final Player player = playerRepository.findById(uuid).orElse(null);
		if (player == null)
			return new Response(false, "Der angegebene Spieler konnte nicht gefunden werden");

		final int currentPoints = player.getPoints();
		final int newPoints = currentPoints + delta;

		player.setPoints(newPoints < 0 ? 0 : newPoints);
		playerRepository.save(player);

		return new Response(true, "Die Punkte wurden erfolgreich aktualisiert");
	}
}
