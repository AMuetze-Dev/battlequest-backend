package de.amit.battlequest.controller.rest.player;

import de.amit.battlequest.model.Player;
import de.amit.battlequest.model.Response;
import de.amit.battlequest.model.Session;
import de.amit.battlequest.controller.rest.session.SessionRessource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
public class PlayerSessionRessource {

    @Autowired
    private PlayerRessource playerRessource;

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private  SessionRessource sessionRessource;

    private boolean checkSP(String code, UUID uuid) {
        return (playerRessource.read(uuid) == null || sessionRessource.read(code) == null) ? false : true;
    }

    public Response delete(@PathVariable UUID uuid){
        Player player = playerRessource.read(uuid);
        if(player == null)
            return new Response(false, "Spieler ist nicht verfügbar.");
        player.setSession(null);
        playerRepository.save(player);
        return new Response(true, "Lobby wurde erfolgreich im Spieler gelöscht.");
    }

    public Response update(@PathVariable UUID uuid, String code){
        Player player = playerRessource.read(uuid);
        Session session = sessionRessource.read(code);
        if(!checkSP(code, uuid))
            return new Response(false, "Spieler oder Lobby ist nicht verfügbar.");
        player.setSession(session);
        playerRepository.save(player);
        return new Response(true, "Lobby wurde erfolgreich im Spieler hinterlegt.");
    }
}
