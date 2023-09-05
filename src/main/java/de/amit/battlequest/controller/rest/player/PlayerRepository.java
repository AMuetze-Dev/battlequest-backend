package de.amit.battlequest.controller.rest.player;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import de.amit.battlequest.model.Player;

public interface PlayerRepository extends JpaRepository<Player, UUID> {
	public Player findByUsername(String username);
	public Player findByUuid(UUID uuid);
}
