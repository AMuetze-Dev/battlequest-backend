package de.amit.battlequest.controller.rest.team;

import de.amit.battlequest.controller.rest.player.PointRessource;
import de.amit.battlequest.model.Player;
import de.amit.battlequest.model.Response;
import de.amit.battlequest.model.Team;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/teams/{id}/points")
public class TeamPointRessource {

    @Autowired
    private TeamRessource teamRessource;

    @Autowired
    private PointRessource pointRessource;

    @PutMapping("/add")
    public Response add(@PathVariable Long id){
        Team team = teamRessource.read(id);
        if(team == null)
            return new Response(false, "Das Team ist nicht verfügbar.");
        List<Player> players = team.getPlayers();
        if(players == null)
            return new Response(false, "In diesem Team sind keine Spieler.");
        for(Player object : players){
            pointRessource.increase(object.getUuid());
        }
        return new Response(true, "Die Punkte wurden erfolgreich hinzugefügt.");
    }

    @PutMapping("/remove")
    public Response remove(@PathVariable Long id){
        Team team = teamRessource.read(id);
        if(team == null)
            return new Response(false, "Das Team ist nicht verfügbar.");
        List<Player> players = team.getPlayers();
        if(players == null)
            return new Response(false, "In diesem Team sind keine Spieler.");
        for(Player object : players){
            pointRessource.decrease(object.getUuid());
        }
        return new Response(true, "Die Punkte wurden erfolgreich abgezogen.");
    }

    @PutMapping("/update/{number}")
    public Response update(@PathVariable Long id, @PathVariable int number){
        Team team = teamRessource.read(id);
        if(team == null)
            return new Response(false, "Das Team ist nicht verfügbar.");
        List<Player> players = team.getPlayers();
        if(players == null)
            return new Response(false, "In diesem Team sind keine Spieler.");
        for(Player object : players){
            pointRessource.update(object.getUuid(), number);
        }
        return new Response(true, "Die Punkte wurden erfolgreich aktualisiert");
    }
}
