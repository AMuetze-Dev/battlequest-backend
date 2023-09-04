package de.amit.battlequest.controller.rest.session;

import de.amit.battlequest.model.Player;
import de.amit.battlequest.model.Response;
import de.amit.battlequest.model.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/session/{code}/players")
public class PlayerRessource {

    @Autowired
    private SessionRepository sessionRepository;

    @Autowired
    private de.amit.battlequest.controller.rest.player.PlayerRessource playerRessource;

    @Autowired
    private de.amit.battlequest.controller.rest.player.SessionRessource playerSessionRessource;

    @Autowired
    private SessionRessource sessionRessource;

    private void addPlayer(Session session, Player player) {
        List<Player> players = session.getPlayers();
        if(!players.contains(player))
            players.add(player);
        session.setPlayers(players);
        sessionRepository.save(session);
    }

    private boolean checkSP(String code, UUID uuid) {
        return (playerRessource.read(uuid) == null || sessionRessource.read(code) == null) ? false : true;
    }

    @DeleteMapping("{uuid}")
    public Response delete(@PathVariable String code, UUID uuid){
        if(!checkSP(code, uuid))
            return new Response(false, "Spieler oder Lobby ist nicht verfügbar.");
        Player player = playerRessource.read(uuid);
        Session session = sessionRessource.read(code);
        playerSessionRessource.delete(uuid);
        removePlayer(session, player);
        return new Response(true, "Spieler wurde erfolgreich aus der Lobby entfernt.");
    }

    private void removePlayer(Session session, Player player) {
        List<Player> players = session.getPlayers();
        if(players.contains(player))
            players.remove(player);
        session.setPlayers(players);
        sessionRepository.save(session);
    }

    @PutMapping("{uuid}")
    public Response update(@PathVariable String code, UUID uuid){
        if(!checkSP(code, uuid))
            return new Response(false, "Spieler oder Lobby ist nicht verfügbar.");
        Player player = playerRessource.read(uuid);
        Session session = sessionRessource.read(code);
        playerSessionRessource.update(uuid, code);
        addPlayer(session, player);
        sessionRepository.save(session);
        return new Response(true, "Spieler wurde erfolgreich der Lobby hinzugefügt.");
    }
}
