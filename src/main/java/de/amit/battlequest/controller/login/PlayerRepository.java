package de.amit.battlequest.controller.login;

import org.springframework.data.jpa.repository.JpaRepository;

import de.amit.battlequest.model.Player;

public interface PlayerRepository extends JpaRepository<Player, Long> {
	public Player findByUsername(String username);
}
