package de.amit.battlequest.controller.rest.team;

import de.amit.battlequest.model.Player;
import de.amit.battlequest.model.Team;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TeamRepository extends JpaRepository<Team, Long> {
    public Object findByTeamname(String name);
    public Object findByTeamId(Long teamId);
}
