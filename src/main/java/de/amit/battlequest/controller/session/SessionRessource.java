package de.amit.battlequest.controller.session;

import de.amit.battlequest.controller.login.PlayerRessource;
import de.amit.battlequest.model.*;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/session")

public class SessionRessource {
    @Autowired
    private SessionRepository sessionRepository;
    @Autowired
    private PlayerRessource playerRessource;

    @PostMapping
    public Response createSession(@RequestBody Long masterId){
        Player master = playerRessource.getPlayerRepository().getById(masterId);
        if(getSession(master) != null)
            return new Response("This user is already the master of a session.", false);
        Session session = new Session();
        session.setMaster(master);
        sessionRepository.save(session);
        return new Response("Session was created successfully", true);
    }
    @PutMapping("/{id}")
    public Response changeMaster(@PathVariable Long id, @RequestBody Player master){
        Session session = getSession(id);
        if(session == null){
            return new Response("This session does not exist.", false);
        }
        if(playerRessource.getUser(master.getId()) == null){
            return new Response("This player does not exist.", false);
        }
        if(getSession(master) != null){
            return new Response("Player is already the master of a session", false);
        }
        session.setMaster(master);
        sessionRepository.save(session);
        return new Response("Master was changed successfully", true);
    }
    @GetMapping("/{id}")
    public Session getSession(@PathVariable Long id){
        return sessionRepository.findById(id).orElse(null);
    }
    @GetMapping
    public Session getSession(@RequestBody Player master){
        // sessionRepository.findByMaster(master.getId());
        return null;
    }
    @DeleteMapping("/{id}")
    public Response deleteSession(@PathVariable Long id){
        Session session = getSession(id);
        if(session == null)
            return new Response("Session does not exist.", false);
        sessionRepository.delete(session);
        return new Response("Session was deleted successfully", true);
    }
}