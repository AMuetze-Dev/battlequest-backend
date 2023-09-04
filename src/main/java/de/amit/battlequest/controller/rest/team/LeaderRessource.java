package de.amit.battlequest.controller.rest.team;

import de.amit.battlequest.controller.rest.player.PlayerRessource;
import de.amit.battlequest.model.Player;
import de.amit.battlequest.model.Response;
import de.amit.battlequest.model.Team;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/teams/{id}/leader")
public class LeaderRessource {

    @Autowired
    private TeamRessource teamRessource;

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private PlayerRessource playerRessource;

    @DeleteMapping
    public Response delete(@PathVariable Long id){
        Team team = teamRessource.read(id);
        if(team.getLeader() == null)
            return new Response(false, "Das Team hat keinen zu Entfernenden Leader.");
        team.setLeader(null);
        teamRepository.save(team);
        return new Response(true, "Der Leader wurde entfernt.");
    }

    @GetMapping
    public Player read(@PathVariable long id){
        return teamRessource.read(id).getLeader() == null ? null : teamRessource.read(id).getLeader();
    }

    @PutMapping("/random")
    public Response update(@PathVariable Long id){
        Team team = teamRessource.read(id);
        if(team.getLeader() != null)
            return new Response(false, "Ein anderer Spieler ist Leader dieses Teams.");
        List<Player> players = team.getPlayers();
        if(players.size() < 1)
            return new Response(false, "Es gibt keine Spieler in diesem Team");
        int i = 0;
        while(team.getLeader() == null) {
            team.setLeader(players.get(i));
        }
        teamRepository.save(team);
        return new Response(true, team.getLeader() + " wurde zum Leader ernannt.");
    }

    @PutMapping
    public Response update(@PathVariable Long id, @RequestBody String username){
        if(!teamRessource.checkUser(id, username))
            return new Response(false, "Dieser Spieler ist kein Mitglied des Teams.");
        Team team = teamRessource.read(id);
        team.setLeader(playerRessource.read(username));
        teamRepository.save(team);
        return new Response( true, "Der Spieler wurde als Leader gesetzt.");
    }
}
