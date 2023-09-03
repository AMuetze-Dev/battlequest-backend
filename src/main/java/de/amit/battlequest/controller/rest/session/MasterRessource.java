package de.amit.battlequest.controller.rest.session;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.amit.battlequest.controller.rest.player.PlayerRessource;
import de.amit.battlequest.model.Player;
import de.amit.battlequest.model.Response;
import de.amit.battlequest.model.Session;

@RestController
@RequestMapping("/session/{code}/master")
public class MasterRessource {

	@Autowired
	private SessionRepository sessionRepository;

	@Autowired
	private PlayerRessource playerRessource;

	@Autowired
	private SessionRessource sessionRessource;

	@DeleteMapping
	public Response delete(@PathVariable String code) {
		final Session session = sessionRessource.read(code);
		if (session == null)
			return new Response(false, "Lobby existiert nicht");
		if (session.getMaster() == null)
			return new Response(false, "Es gibt keinen Spielleiter");
		session.setMaster(null);
		sessionRepository.save(session);
		return new Response(true, "Spielleiter wurde entfernt");
	}

	@PutMapping
	public Response update(@PathVariable String code, @RequestBody UUID masterUuid) {
		final Session session = sessionRessource.read(code);
		if (session == null)
			return new Response(false, "Lobby existiert nicht");
		final Player master = playerRessource.read(masterUuid);
		if (master == null)
			return new Response(false, "Spielleiter existiert nicht");
		if (session.getMaster() != null && session.getMaster().getUuid().equals(masterUuid))
			return new Response(false, "Ist bereits Spielleiter der Lobby");
		session.setMaster(master);
		sessionRepository.save(session);
		return new Response(true, "Spielleiter wurde gesetzt");
	}

}
