package de.amit.battlequest.controller.rest.session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.amit.battlequest.model.Response;
import de.amit.battlequest.model.Session;

@RestController
@RequestMapping("/session")
public class SessionRessource {

	@Autowired
	private SessionRepository sessionRepository;

	@PostMapping
	public Response create() {
		final Session session = new Session();
		sessionRepository.save(session);
		return new Response(true, "Lobby wurde angelegt");
	}

	@DeleteMapping("/{code}")
	public Response delete(@PathVariable String code) {
		final Session session = read(code);
		if (session == null)
			return new Response(false, "Lobby existiert nicht");
		sessionRepository.delete(session);
		return new Response(true, "Lobby wurde gelöscht");
	}

	@GetMapping("/{code}")
	public Session read(@PathVariable String code) {
		return sessionRepository.findById(code).orElse(null);
	}

}
