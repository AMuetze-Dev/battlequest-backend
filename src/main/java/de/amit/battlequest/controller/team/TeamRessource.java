package de.amit.battlequest.controller.team;

import de.amit.battlequest.controller.rest.player.PlayerRessource;
import de.amit.battlequest.model.Player;
import de.amit.battlequest.model.Response;
import de.amit.battlequest.model.Team;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/teams")
public class TeamRessource {

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private PlayerRessource playerRessource;

    @PutMapping("/add/{id}")
    public Response addPlayer(@PathVariable Long id, @RequestBody String username){
        Player player = playerRessource.read(username);
        Team team = getTeam(id);
        if(!checkUser(id, username))
            return new Response(false, "Session oder Spieler ist nicht verfügbar.");
        if(player.getTeam() != null)
            return new Response(false,"Spieler ist schon Teil eines Teams.");
        player.setTeam(team);
        return new Response(true, "Spieler wurde erfolgreich zum Team hinzugefügt." );
    }

    public boolean checkName(Long id, String name){
        Team team = getTeam(id);
        return !(team == null || teamRepository.findByName(name) != null);
    }

    public boolean checkUser(Long id, String name){
        return !(getTeam(id) == null || playerRessource.read(name) == null);
    }

    @PostMapping
    public Response createTeam(){
        Team team = new Team();
        teamRepository.save(team);
        return new Response(false, "Team wurde erstellt.");
    }

    @DeleteMapping("/team/{id}")
    public Response deleteTeam(@PathVariable Long id){
        if(getTeam(id) == null)
            return new Response(false,"Team ist nicht verfügbar.");
        teamRepository.delete(getTeam(id));
        return new Response(true,  "Team wurde gelöscht.");
    }

    @GetMapping("/{id}")
    public Team getTeam(@PathVariable Long id){
        return teamRepository.findById(id).orElse(null);
    }

    @GetMapping
    public Team getTeam(@RequestBody String name){
        return teamRepository.findByName(name);
    }

    @PutMapping("/{id}")
    public Response selectLeader(@PathVariable Long id){
        if(getTeam(id).getLeader() != null)
            return new Response(false, "Ein anderer Spieler ist Leader dieses Teams.");
        //TODO: iterate through players to find any with {id} as teamid. HOW?
        return null;
    }

    @PutMapping("/leader/{id}")
    public Response setLeader(@PathVariable Long id, @RequestBody String username){
        if(!checkUser(id, username))
            return new Response(false, "Dieser Teamname ist schon vergeben.");
        Team team = getTeam(id);
        team.setLeader(playerRessource.read(username));
        teamRepository.save(team);
        return new Response( true, "Teamname wurde geändert.");
    }

    @PutMapping("/name/{id}")
    public Response setName(@PathVariable Long id, @RequestBody String name){
        Team team = getTeam(id);
        if(!checkName(id, name))
            return new Response(false, "Team oder Name ist nicht verfügbar.");
        team.setTeamname(name);
        teamRepository.save(team);
        return new Response(true, "Teamname wurde geändert.");
    }

    @DeleteMapping("/leader/{id}")
    public Response removeLeader(@PathVariable Long id){
        Team team = getTeam(id);
        if(team.getLeader() == null)
            return new Response(false, "Das Team hat keinen zu Entfernenden Leader.");
        team.setLeader(null);
        teamRepository.save(team);
        return new Response(true, "Der Leader wurde entfernt.");
    }

    @DeleteMapping("/player/{id}")
    public Response removePLayer(@PathVariable Long id, @RequestBody  String username){
        if(!checkUser(id, username))
            return new Response(false, "Team oder Spieler ist nicht verfügbar.");
        playerRessource.read(username).setTeam(null);
        return new Response(true, "Der Spieler wurde entfernt.");
    }

    @PutMapping("/reward/{id}")
    public Response addPoints(@PathVariable Long id, @RequestBody int points){
        Team team = getTeam(id);
        if(team == null)
            return new Response(false, "Das Team ist nicht verfügbar.");
        //TODO: iterate through all the team members with for each
        return null;
    }
}