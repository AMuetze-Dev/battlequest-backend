package de.amit.battlequest.controller.rest.sessionplayers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sessionplayers")
public class SPRessource {
//	@Autowired
//	private SessionPlayersRepository spRepository;
//	@Autowired
//	private SessionRessourceOld sessionRessource;
//	@Autowired
//	private PlayerRessourceOld playerRessource;
//
//	//
//
//	@PostMapping("/{id}")
//	public Response addPlayer(@PathVariable String id, @RequestBody String username) {
//		if (checkValidity(id, username) == false)
//			return new Response(false, "Session or Player are invalid.");
//		if (getSession(playerRessource.getUser(username)) != null)
//			return new Response(false, "That Player is already in a session.");
//		final SessionIdentity sessionIdentity = new SessionIdentity(sessionRessource.getSession(id),
//				playerRessource.getUser(username));
//		spRepository.save(sessionIdentity);
//		if (!sessionRessource.hasMaster(id))
//			sessionRessource.getSession(id).setMaster(sessionIdentity.getPlayer());
//		return new Response(true, "Player " + username + " was added to Session " + id + " successfully.");
//	}
//
//	public boolean checkValidity(String id, String username) {
//		if (sessionRessource.getSession(id) == null || playerRessource.getUser(username) == null)
//			return false;
//		return true;
//	}
//
//	@DeleteMapping
//	public Response deletePlayer(@RequestBody String username) {
//		if (getSession(playerRessource.getUser(username)) == null)
//			return new Response(false, "This player was not assigned to any session.");
//		spRepository.delete(spRepository.findByPlayer(playerRessource.getUser(username)));
//		return new Response(true, "Player was successfully kicked out of the session.");
//	}
//
//	@GetMapping("/{id}")
//	public List<Player> getPlayers(@PathVariable String id) {
//		final List<SessionIdentity> sessionList = spRepository.findBySession(sessionRessource.getSession(id));
//		final List<Player> playerList = new ArrayList<>();
//		for (final SessionIdentity object : sessionList)
//			playerList.add(object.getPlayer());
//		return playerList;
//	}
//
//	@GetMapping
//	public Session getSession(@RequestBody Player player) {
//		return spRepository.findByPlayer(player) == null ? null : spRepository.findByPlayer(player).getSession();
//	}
//
//	@PutMapping("/{id}")
//	public Response setSession(@PathVariable String id, @RequestBody String username) {
//		if (checkValidity(id, username) == false)
//			return new Response(false, "Session or Player are invalid.");
//		if (getSession(playerRessource.getUser(username)) == null)
//			return new Response(false, "That Player hasn't been assigned a session.");
//		if (getSession(playerRessource.getUser(username)).getSessionId() == id)
//			return new Response(false, "That player is already in the requested session.");
//		final SessionIdentity sessionIdentity = spRepository.findByPlayer(playerRessource.getUser(username));
//		sessionIdentity.setSession(sessionRessource.getSession(id));
//		spRepository.save(sessionIdentity);
//		return new Response(true, "Player " + username + " was moved to Session " + id + " successfully.");
//	}
}
