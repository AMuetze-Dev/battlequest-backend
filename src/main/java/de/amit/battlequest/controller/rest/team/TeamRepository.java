package de.amit.battlequest.controller.rest.team;

import de.amit.battlequest.model.Player;
import de.amit.battlequest.model.Team;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamRepository extends JpaRepository<Team, Long> {
    public Team findByLeader(Player leader);
    public Team findByTeamname(String name);
}
