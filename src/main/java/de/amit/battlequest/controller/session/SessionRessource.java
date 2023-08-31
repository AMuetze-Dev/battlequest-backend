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
    public Response createSession(){
        Session session = new Session();
        sessionRepository.save(session);
        return new Response("Session was created successfully", true);
    }
    @PutMapping("/master/set={id}")
    public Response setMaster1(@PathVariable String id, @RequestBody String username){
        Session session = getSession(id);
        Player master = playerRessource.getUser(username);
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
    @DeleteMapping("/master/delete={id}")
    public Response removeMaster(@PathVariable String id){
        Session session = getSession(id);
        if(session.getMaster()==null)
            return new Response("There is no master in this session", false);
        session.setMaster(null);
        sessionRepository.save(session);
        return new Response("Master was removed successfully", true);
    }
    @GetMapping("/{id}")
    public Session getSession(@PathVariable String id){
        return sessionRepository.findById(id).orElse(null);
    }
    @GetMapping
    public Session getSession(@RequestBody Player master){
        return sessionRepository.findByMaster(master);
    }
    @DeleteMapping("/{id}")
    public Response deleteSession(@PathVariable String id){
        Session session = getSession(id);
        if(session == null)
            return new Response("Session does not exist.", false);
        sessionRepository.delete(session);
        return new Response("Session was deleted successfully", true);
    }
}