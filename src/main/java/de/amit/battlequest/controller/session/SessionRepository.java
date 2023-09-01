package de.amit.battlequest.controller.session;

import de.amit.battlequest.model.Player;
import de.amit.battlequest.model.Session;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SessionRepository extends JpaRepository<Session, String> {
    public Session findByMaster(Player master);
}