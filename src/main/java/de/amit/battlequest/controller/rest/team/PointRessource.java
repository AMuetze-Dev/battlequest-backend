package de.amit.battlequest.controller.rest.team;

import de.amit.battlequest.model.Player;
import de.amit.battlequest.model.Response;
import de.amit.battlequest.model.Team;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/teams/{id}/points")
public class PointRessource {

    @Autowired
    private TeamRessource teamRessource;

    @Autowired
    private de.amit.battlequest.controller.rest.player.PointRessource playerPointRessource;

    @PutMapping("/add")
    public Response add(@PathVariable Long id){
        Team team = teamRessource.read(id);
        if(team == null)
            return new Response(false, "Das Team ist nicht verfügbar.");
        List<Player> players = team.getPlayers();
        for(Player object : players){
            playerPointRessource.increase(object.getUuid());
        }
        return new Response(true, "Die Punkte wurden erfolgreich hinzugefügt.");
    }

    @PutMapping("/remove")
    public Response remove(@PathVariable Long id){
        Team team = teamRessource.read(id);
        if(team == null)
            return new Response(false, "Das Team ist nicht verfügbar.");
        List<Player> players = team.getPlayers();
        for(Player object : players){
            playerPointRessource.decrease(object.getUuid());
        }
        return new Response(true, "Die Punkte wurden erfolgreich abgezogen.");
    }

    @PutMapping("/update/{number}")
    public Response update(@PathVariable Long id, int number){
        Team team = teamRessource.read(id);
        if(team == null)
            return new Response(false, "Das Team ist nicht verfügbar.");
        List<Player> players = team.getPlayers();
        for(Player object : players){
            playerPointRessource.update(object.getUuid(), number);
        }
        return new Response(true, "Die Punkte wurden erfolgreich aktualisiert");
    }
}
