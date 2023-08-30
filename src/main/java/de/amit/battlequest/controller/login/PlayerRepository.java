package de.amit.battlequest.controller.login;

import de.amit.battlequest.model.Player;
import de.amit.battlequest.model.Test;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PlayerRepository extends JpaRepository<Player, Long>{
    public Player findByUsername(String username);
}
