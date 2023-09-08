package de.amit.battlequest.controller.rest.team;

import de.amit.battlequest.model.Player;
import de.amit.battlequest.controller.rest.player.PlayerRessource;
import de.amit.battlequest.model.Response;
import de.amit.battlequest.model.Team;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/teams/{id}/players/{uuid}")
public class TeamPlayerRessource {

    @Autowired
    private TeamRessource teamRessource;

    @Autowired
    private PlayerRessource playerRessource;

    @DeleteMapping
    public Response delete(@PathVariable Long id, @PathVariable  UUID uuid){
        Player player = playerRessource.read(uuid);
        if(!teamRessource.checkUser(id, uuid))
            return new Response(false, "Team oder Spieler ist nicht verfügbar.");
        player.setTeam(null);
        return new Response(true, "Der Spieler wurde entfernt.");
    }

    @PutMapping
    public Response update(@PathVariable Long id, @PathVariable UUID uuid){
        Player player = playerRessource.read(uuid);
        Team team = teamRessource.read(id);
        if(!teamRessource.checkUser(id, uuid))
            return new Response(false, "Lobby oder Spieler ist nicht verfügbar.");
        if(teamRessource.getTeam(player) != null)
            return new Response(false,"Spieler ist schon Teil eines Teams.");
        player.setTeam(team);
        return new Response(true, "Spieler wurde erfolgreich zum Team hinzugefügt." );
    }
}
