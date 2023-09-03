package de.amit.battlequest.controller.rest.sessionplayers;

import de.amit.battlequest.model.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SessionPlayersRepository extends JpaRepository<SessionIdentity, Long> {
    public SessionIdentity findByPlayer(Player player);
    public List<SessionIdentity> findBySession(Session session);
}
