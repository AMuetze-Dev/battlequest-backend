package de.amit.battlequest.controller.rest.session;

import de.amit.battlequest.controller.rest.player.PlayerSessionRessource;
import de.amit.battlequest.controller.rest.player.PlayerRessource;
import de.amit.battlequest.model.Player;
import de.amit.battlequest.model.Response;
import de.amit.battlequest.model.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/session/{code}/players")
public class SessionPlayerRessource {

    @Autowired
    private SessionRepository sessionRepository;

    @Autowired
    private PlayerRessource playerRessource;

    @Autowired
    private PlayerSessionRessource playerSessionRessource;

    @Autowired
    private SessionRessource sessionRessource;


    private boolean checkAllSessions(Player player, Session session) {
        return (player.getSession() == null || player.getSession() != session) ? false : true;
    }

    private boolean checkSP(String code, UUID uuid) {
        return (playerRessource.read(uuid) == null || sessionRessource.read(code) == null) ? false : true;
    }

    @DeleteMapping("{uuid}")
    public Response delete(@PathVariable String code, UUID uuid){
        Player player = playerRessource.read(uuid);
        Session session = sessionRessource.read(code);
        if(!checkSP(code, uuid))
            return new Response(false, "Spieler oder Lobby ist nicht verfügbar.");
        if(!checkAllSessions(player, session))
            return new Response(false, "Spieler ist in einer anderen Lobby.");
        playerSessionRessource.delete(uuid);
        return new Response(true, "Spieler wurde erfolgreich aus der Lobby entfernt.");
    }

    @PutMapping("{uuid}")
    public Response update(@PathVariable String code, UUID uuid){
        Player player = playerRessource.read(uuid);
        Session session = sessionRessource.read(code);
        if(!checkSP(code, uuid))
            return new Response(false, "Spieler oder Lobby ist nicht verfügbar.");
        if(!checkAllSessions(player, session))
            return new Response(false, "Spieler ist bereits in einer anderen Lobby.");
        playerSessionRessource.update(uuid, code);
        sessionRepository.save(session);
        return new Response(true, "Spieler wurde erfolgreich der Lobby hinzugefügt.");
    }
}
