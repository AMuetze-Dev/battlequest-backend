package de.amit.battlequest.controller.rest.team;

import de.amit.battlequest.model.Player;
import de.amit.battlequest.model.Response;
import de.amit.battlequest.model.Team;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/teams/{id}/players")
public class PlayerRessource {

    @Autowired
    private TeamRessource teamRessource;

    @Autowired
    private de.amit.battlequest.controller.rest.player.PlayerRessource playerRessource;

    @DeleteMapping("/{id}")
    public Response delete(@PathVariable Long id, @RequestBody String username){
        if(!teamRessource.checkUser(id, username))
            return new Response(false, "Team oder Spieler ist nicht verfügbar.");
        playerRessource.read(username).setTeam(null);
        return new Response(true, "Der Spieler wurde entfernt.");
    }

    @PutMapping("/add/{id}")
    public Response update(@PathVariable Long id, @RequestBody String username){
        Player player = playerRessource.read(username);
        Team team = teamRessource.read(id);
        if(!teamRessource.checkUser(id, username))
            return new Response(false, "Lobby oder Spieler ist nicht verfügbar.");
        if(player.getTeam() != null)
            return new Response(false,"Spieler ist schon Teil eines Teams.");
        player.setTeam(team);
        return new Response(true, "Spieler wurde erfolgreich zum Team hinzugefügt." );
    }
}
