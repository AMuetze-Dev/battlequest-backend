package de.amit.battlequest.controller.team;

import de.amit.battlequest.controller.login.PlayerRessource;
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
        Player player = playerRessource.getUser(username);
        Team team = getTeam(id);
        if(!checkUser(id, username))
            return new Response("The requested Session or Player is not available.", false);
        if(player.getTeam() != null)
            return new Response("Player is already part of another team.", false);
        playerRessource.setTeam(player.getId(), team);
        if(team.getLeader() == null) {
            setLeader(id, username);
            return new Response(player.getNickname() + " was successfully assigned leader of " + team.getName(), true)
        }
        return new Response(player.getNickname() + " was successfully assigned to Team " + team.getName(), true);
    }

    public boolean checkName(Long id, String name){
        Team team = getTeam(id);
        if(team == null || teamRepository.findByName(name) != null)
            return false;
        return true;
    }

    public boolean checkUser(Long id, String name){
        if(getTeam(id) == null || playerRessource.getUser(name) == null)
            return false;
        return true;
    }

    @PostMapping
    public Response createTeam(){
        Team team = new Team();
        teamRepository.save(team);
        return new Response("Team was created successfully.", false);
    }

    @DeleteMapping("/team/{id}")
    public Response deleteTeam(@PathVariable Long id){
        if(getTeam(id) == null)
            return new Response("The requested Team is unavailable.", false);
        teamRepository.delete(getTeam(id));
        return new Response(getTeam(id).getName() + " was deleted successfully.", true);
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
            return new Response("There is still a leader in place.", false);
        //TODO: iterate through players to find any with {id} as teamid. HOW?
        return null;
    }

    @PutMapping("/leader/{id}")
    public Response setLeader(@PathVariable Long id, @RequestBody String username){
        if(!checkUser(id, username))
            return new Response("The requested team or name is not available.", false);
        Team team = getTeam(id);
        team.setLeader(playerRessource.getUser(username));
        teamRepository.save(team);
        return new Response(team.getLeader() + " was successfully assigned leader of " + team.getName(), true);
    }

    @PutMapping("/name/{id}")
    public Response setName(@PathVariable Long id, @RequestBody String name){
        Team team = getTeam(id);
        if(!checkName(id, name))
            return new Response("The requested team or name is not available.", false);
        team.setName(name);
        teamRepository.save(team);
        return new Response("Team name was changed successfully.", true);
    }

    @DeleteMapping("/leader/{id}")
    public Response removeLeader(@PathVariable Long id){
        Team team = getTeam(id);
        if(team.getLeader() == null)
            return new Response("The requested team does not have a leader.", false);
        team.setLeader(null);
        teamRepository.save(team);
        return new Response("The team leader of " + team.getName() + " was removed successfully.", true);
    }

    @DeleteMapping("/player/{id}")
    public Response removePLayer(@PathVariable Long id, @RequestBody  String username){
        if(!checkUser(id, username))
            return new Response("The requested team or player is not available.", false);
        playerRessource.getUser(username).setTeam(null);
        return new Response(playerRessource.getUser(username).getNickname() + "was successfully removed from " + getTeam(id).getName(), true);
    }

    @PutMapping("/reward/{id}")
    public Response addPoints(@PathVariable Long id, @RequestBody int points){
        Team team = getTeam(id);
        if(team == null)
            return new Response("The requested teamis not available", false);
        //TODO: iterate through all the team members with for each
        return null;
    }
}
