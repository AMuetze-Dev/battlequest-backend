package de.amit.battlequest.controller.login;

import de.amit.battlequest.model.Player;
import de.amit.battlequest.model.Test;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PlayerRepository extends JpaRepository<Player, Long>{
    public Player findByUsername(String username);
}
