package de.amit.battlequest.controller.rest.team;

import de.amit.battlequest.model.Response;
import de.amit.battlequest.model.Team;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/teams/{id}/name")
public class NameRessource {

    @Autowired
    private TeamRessource teamRessource;

    @Autowired
    private TeamRepository teamRepository;

    @PutMapping
    public Response update(@PathVariable Long id, @RequestBody String name){
        Team team = teamRessource.read(id);
        if(team == null)
            return new Response(false, "Das Team existiert nicht.");
        if((teamRessource.read(teamRessource.readName(name))) != null)
            return new Response(false, "Der Name ist schon vergeben.");
        team.setTeamname(name);
        teamRepository.save(team);
        return new Response(true, "Teamname wurde geändert.");
    }
}
