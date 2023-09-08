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
@RequestMapping("/teams")
public class TeamRessource {

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private PlayerRessource playerRessource;



    public boolean checkName(Long id, String name){
        Team team = read(id);
        return !(team == null || teamRepository.findByTeamname(name) != null);
    }

    public boolean checkUser(Long id, UUID uuid){
        return !(read(id) == null && playerRessource.read(uuid) == null);
    }

    @PostMapping
    public Response create(){
        Team team = new Team();
        teamRepository.save(team);
        return new Response(true, "Team wurde erstellt.");
    }

    @DeleteMapping("/{id}")
    public Response delete(@PathVariable Long id){
        if(read(id) == null)
            return new Response(false,"Team ist nicht verfügbar.");
        teamRepository.delete(read(id));
        return new Response(true,  "Team wurde gelöscht.");
    }

    public Player getLeader(Long id) {
        return read(id).getLeader();
    }

    public int getNumberPlayers(Team team) {
        return team.getPlayers().size();
    }

    public List<Player> getPlayers(Team team) {
        return team.getPlayers();
    }

    public Team getTeam(Player player) {
        return player.getTeam();
    }

    @GetMapping("/{id}")
    public Team read(@PathVariable Long id){
        Object team = teamRepository.findByTeamId(id);
        if(team == null)
            return null;
        return (Team) teamRepository.findByTeamId(id);
    }

    @GetMapping
    public Team read(@RequestBody String name){
        Object team = teamRepository.findByTeamname(name);
        if(team == null)
            return null;
        return (Team) teamRepository.findByTeamname(name);
    }

    public Team readId(Long id) {
        return teamRepository.findById(id).orElse(null);
    }

    public Long readName(String name){
        Object team = teamRepository.findByTeamname(name);
        if(team == null)
            return null;
        return ((Team) teamRepository.findByTeamname(name)).getTeamId();
    }
}