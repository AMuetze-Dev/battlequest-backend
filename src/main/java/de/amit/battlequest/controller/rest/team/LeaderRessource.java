package de.amit.battlequest.controller.rest.team;

import de.amit.battlequest.controller.rest.player.PlayerRessource;
import de.amit.battlequest.model.Player;
import de.amit.battlequest.model.Response;
import de.amit.battlequest.model.Team;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

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
        if(team == null)
            return new Response(false, "Das Team existiert nicht.");
        if(teamRessource.getLeader(id) == null)
            return new Response(false, "Das Team hat keinen zu Entfernenden Leader.");
        team.setLeader(null);
        teamRepository.save(team);
        return new Response(true, "Der Leader wurde entfernt.");
    }

    @GetMapping
    public Player read(@PathVariable long id){
        return teamRessource.getLeader(id);
    }

    @PutMapping("/random")
    public Response update(@PathVariable Long id){
        Team team = teamRessource.read(id);
        if(team == null)
            return new Response(false, "Das Team existiert nicht.");
        List<Player> players = team.getPlayers();
        if(teamRessource.getNumberPlayers(team) < 1 || teamRessource.getPlayers(team) == null)
            return new Response(false, "Es gibt keine Spieler in diesem Team");
        if(teamRessource.getLeader(id) != null)
            return new Response(false, "Es gibt bereits einen Leader.");
        int i = 0;
        while(team.getLeader() == null) {
            team.setLeader(players.get(i));
        }
        teamRepository.save(team);
        return new Response(true, "Der Spieler wurde zum Leader ernannt.");
    }

    @PutMapping("/{uuid}")
    public Response update(@PathVariable Long id, @PathVariable UUID uuid){
        Player player = playerRessource.read(uuid);
        if(player == null)
            return new Response(false, "Der Spieler existiert nicht.");
        Team team = teamRessource.read(id);
        if(team == null)
            return new Response(false, "Das Team existiert nicht");
        if(teamRessource.getTeam(player) != team)
            return new Response(false, "Dieser Spieler ist kein Mitglied des Teams.");
        team.setLeader(player);
        teamRepository.save(team);
        return new Response( true, "Der Spieler wurde als Leader gesetzt.");
    }
}
