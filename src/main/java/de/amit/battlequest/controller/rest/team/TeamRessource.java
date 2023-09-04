package de.amit.battlequest.controller.rest.team;

import de.amit.battlequest.controller.rest.player.PlayerRessource;
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



    public boolean checkName(Long id, String name){
        Team team = read(id);
        return !(team == null || teamRepository.findByName(name) != null);
    }

    public boolean checkUser(Long id, String name){
        return !(read(id) == null || playerRessource.read(name) == null);
    }

    @PostMapping
    public Response create(){
        Team team = new Team();
        teamRepository.save(team);
        return new Response(false, "Team wurde erstellt.");
    }

    @DeleteMapping("/{id}")
    public Response delete(@PathVariable Long id){
        if(read(id) == null)
            return new Response(false,"Team ist nicht verfügbar.");
        teamRepository.delete(read(id));
        return new Response(true,  "Team wurde gelöscht.");
    }

    @GetMapping("/{id}")
    public Team read(@PathVariable Long id){
        return teamRepository.findById(id).orElse(null);
    }

    @GetMapping
    public Team read(@RequestBody String name){
        return teamRepository.findByName(name);
    }
}