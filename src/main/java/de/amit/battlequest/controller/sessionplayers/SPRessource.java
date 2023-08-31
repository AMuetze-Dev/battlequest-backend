package de.amit.battlequest.controller.sessionplayers;

import de.amit.battlequest.controller.login.PlayerRessource;
import de.amit.battlequest.controller.session.SessionRessource;
import de.amit.battlequest.model.Player;
import de.amit.battlequest.model.Response;
import de.amit.battlequest.model.Session;
import de.amit.battlequest.model.SessionIdentity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sessionplayers")
public class SPRessource {
    @Autowired
    private SPRepository spRepository;
    @Autowired
    private SessionRessource sessionRessource;
    @Autowired
    private PlayerRessource playerRessource;

    @PostMapping("/{id}")
    public Response addPlayer(@PathVariable String id, @RequestBody String username){
        if(checkValidity(id, username) == false)
            return new Response("Session or Player are invalid.", false);
        if(checkPlayerSession(playerRessource.getUser(username)) != null)
            return new Response("That Player is already in a session.", false);
        SessionIdentity sessionIdentity= new SessionIdentity(sessionRessource.getSession(id), playerRessource.getUser(username));
        spRepository.save(sessionIdentity);
        return new Response("Player " + username + " was added to Session " + id + " successfully.", true);
    }
    @PutMapping("/{id]")
    public Response changeSession(@PathVariable String id, @RequestBody String username){
        if(checkValidity(id, username) == false)
            return new Response("Session or Player are invalid.", false);
        if(checkPlayerSession(playerRessource.getUser(username)) == null)
            return new Response("That Player hasn't been assigned a session.", false);
        if(checkPlayerSession(playerRessource.getUser(username)).getSessionId() == id){
            return new Response("That player is already in the requested session.", false);
        }
        SessionIdentity sessionIdentity = spRepository.findByPlayer(playerRessource.getUser(username));
        sessionIdentity.setSession(sessionRessource.getSession(id));
        spRepository.save(sessionIdentity);
        return new Response("Player " + username + " was moved to Session " + id + " successfully.", true);
    }
    @GetMapping
    public Session checkPlayerSession(@RequestBody Player player){
        System.out.println(player.getId());
        return spRepository.findByPlayer(player) == null ? null : spRepository.findByPlayer(player).getSession();
    }
    @GetMapping("/{id}")
    public List<SessionIdentity> checkSession(@PathVariable String id){
        return spRepository.findBySession(sessionRessource.getSession(id));
    }
    public boolean checkValidity(String id, String username){
        if(sessionRessource.getSession(id) == null || playerRessource.getUser(username) == null)
            return false;
        return true;
    }
}
